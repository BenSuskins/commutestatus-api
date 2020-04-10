package uk.co.suskins.commutestatus.user.service;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import com.auth0.net.Request;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.suskins.commutestatus.common.models.entities.Station;
import uk.co.suskins.commutestatus.common.models.entities.User;
import uk.co.suskins.commutestatus.common.models.entities.UserPreference;
import uk.co.suskins.commutestatus.common.models.exceptions.CommuteStatusServiceException;
import uk.co.suskins.commutestatus.common.models.exceptions.ErrorCodes;
import uk.co.suskins.commutestatus.common.repository.StationRepository;
import uk.co.suskins.commutestatus.common.repository.UserPreferenceRepository;
import uk.co.suskins.commutestatus.common.repository.UserRepository;
import uk.co.suskins.commutestatus.config.auth0.Auth0Config;
import uk.co.suskins.commutestatus.user.models.api.UserRequest;
import uk.co.suskins.commutestatus.user.models.mapper.UserMapper;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    public static final String AUTH0_CONNECTION = "Username-Password-Authentication";
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final Auth0Config auth0Config;

    @Autowired
    public UserService(UserMapper userMapper,
                       UserRepository userRepository,
                       StationRepository stationRepository,
                       UserPreferenceRepository userPreferenceRepository,
                       Auth0Config auth0Config) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.stationRepository = stationRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.auth0Config = auth0Config;

    }

    public void postUser(UserRequest userRequest) {
        try {
            //Check that the user doesn't already exist
            Optional<User> user = userRepository.findByEmail(userRequest.getEmail());

            if (user.isPresent()) {
                throw new CommuteStatusServiceException(ErrorCodes.USER_ALREADY_EXISTS);
            }

            //Check that the stations provided are valid
            Optional<Station> homeStation = stationRepository.findById(userRequest.getHomeStationID());
            Optional<Station> workStation = stationRepository.findById(userRequest.getWorkStationID());

            if (homeStation.isEmpty() || workStation.isEmpty()) {
                throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_STATION_ID);
            }

            //Create user
            User userToCreate = User.builder()
                    .authId(createAuth0User(userRequest))
                    .email(userRequest.getEmail())
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .dateCreated(new Date())
                    .build();
            User createdUser = userRepository.save(userToCreate);

            //Create user preferences
            UserPreference userPreferenceToCreate = UserPreference.builder()
                    .user(createdUser)
                    .homeStation(homeStation.get())
                    .workStation(workStation.get())
                    .build();
            userPreferenceRepository.save(userPreferenceToCreate);
        } catch (CommuteStatusServiceException ex) {
            throw ex;
        } catch (DataException ex) {
            log.error("[{}] {} During postUser",
                    ErrorCodes.DATABASE_ERROR.getErrorCode(),
                    ErrorCodes.DATABASE_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.DATABASE_ERROR);
        } catch (Exception ex) {
            log.error("[{}] {} During postUser",
                    ErrorCodes.UNKNOWN_ERROR.getErrorCode(),
                    ErrorCodes.UNKNOWN_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_ERROR);
        }
    }

    private String createAuth0User(UserRequest userRequest) {
        try {
            //Create Auth0 Management API connecction
            AuthAPI authAPI = new AuthAPI(auth0Config.getDomain(),
                    auth0Config.getClientId(), auth0Config.getClientSecret());
            AuthRequest authRequest =
                    authAPI.requestToken(auth0Config.getDomain() + "/api/v2/");
            TokenHolder holder = authRequest.execute();
            ManagementAPI mgmt =
                    new ManagementAPI(auth0Config.getDomain(), holder.getAccessToken());
            com.auth0.json.mgmt.users.User data = new com.auth0.json.mgmt.users.User(AUTH0_CONNECTION);

            //Create update payload
            data.setEmail(userRequest.getEmail());
            data.setPassword(userRequest.getPassword());
            Request<com.auth0.json.mgmt.users.User> request = mgmt.users().create(data);

            //Execute update
            com.auth0.json.mgmt.users.User response = request.execute();
            return response.getId();
        } catch (APIException ex) {
            log.error("[{}] {} During createAuth0User",
                    ErrorCodes.AUTH0_API_ERROR.getErrorCode(), ErrorCodes.AUTH0_API_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.AUTH0_API_ERROR);
        } catch (Auth0Exception ex) {
            log.error("[{}] {} During createAuth0User",
                    ErrorCodes.AUTH0_REQUEST_ERROR.getErrorCode(), ErrorCodes.AUTH0_REQUEST_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.AUTH0_REQUEST_ERROR);
        } catch (Exception ex) {
            log.error("[{}] {} During createAuth0User",
                    ErrorCodes.UNKNOWN_ERROR.getErrorCode(), ErrorCodes.UNKNOWN_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_ERROR);
        }
    }

    public void putUser(Long userId, UserRequest userRequest) {
        try {
            //Get the user
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                log.error("[{}] {} During putUser for user ID {}",
                        ErrorCodes.USER_NOT_FOUND.getErrorCode(), ErrorCodes.USER_NOT_FOUND.getErrorTitle(), userId);
                throw new CommuteStatusServiceException(ErrorCodes.USER_NOT_FOUND);
            }

            //Then we update the user
            User user = optionalUser.get();
            if (Objects.nonNull(userRequest.getLastName())) {
                user.setLastName(userRequest.getLastName());
            }
            if (Objects.nonNull(userRequest.getFirstName())) {
                user.setFirstName(userRequest.getFirstName());
            }
            if (Objects.nonNull(userRequest.getEmail())) {
                user.setEmail(userRequest.getEmail());
            }
            user.setDateUpdated(new Date());
            userRepository.save(user);

            //Then we update Auth0 if the email or password changed
            if (Objects.nonNull(userRequest.getEmail()) || Objects.nonNull(userRequest.getPassword())) {
                updateAuth0User(userRequest, user.getAuthId());
            }

            //Get the users preferences
            Optional<UserPreference> optionalUserPreference = userPreferenceRepository.findByUserId(userId);
            if (optionalUserPreference.isEmpty()) {
                log.error("[{}] {} During putUser for user ID {}",
                        ErrorCodes.USER_NOT_FOUND.getErrorCode(), ErrorCodes.USER_NOT_FOUND.getErrorTitle(), userId);
                throw new CommuteStatusServiceException(ErrorCodes.USER_NOT_FOUND);
            }

            //Then we update user preference
            UserPreference userPreference = optionalUserPreference.get();

            if (Objects.nonNull(userRequest.getWorkStationID())) {
                Optional<Station> workStation = stationRepository.findById(userRequest.getWorkStationID());
                if (workStation.isEmpty()) {
                    throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_STATION_ID);
                }
                userPreference.setWorkStation(workStation.get());
            }

            if (Objects.nonNull(userRequest.getHomeStationID())) {
                Optional<Station> homeStation = stationRepository.findById(userRequest.getHomeStationID());
                if (homeStation.isEmpty()) {
                    throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_STATION_ID);
                }
                userPreference.setWorkStation(homeStation.get());
            }

            userPreferenceRepository.save(userPreference);
        } catch (CommuteStatusServiceException ex) {
            throw ex;
        } catch (DataException ex) {
            log.error("[{}] {} During postUser",
                    ErrorCodes.DATABASE_ERROR.getErrorCode(),
                    ErrorCodes.DATABASE_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.DATABASE_ERROR);
        } catch (Exception ex) {
            log.error("[{}] {} During postUser",
                    ErrorCodes.UNKNOWN_ERROR.getErrorCode(),
                    ErrorCodes.UNKNOWN_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_ERROR);
        }
    }

    private void updateAuth0User(UserRequest userRequest, String authId) {
        try {
            //Create Auth0 Management API connecction
            AuthAPI authAPI = new AuthAPI(auth0Config.getDomain(),
                    auth0Config.getClientId(), auth0Config.getClientSecret());
            AuthRequest authRequest =
                    authAPI.requestToken(auth0Config.getDomain() + "/api/v2/");
            TokenHolder holder = authRequest.execute();
            ManagementAPI mgmt =
                    new ManagementAPI(auth0Config.getDomain(), holder.getAccessToken());
            com.auth0.json.mgmt.users.User data = new com.auth0.json.mgmt.users.User();

            //Create update payload
            if (Objects.nonNull(userRequest.getEmail())) {
                data.setEmail(userRequest.getEmail());
                data.setName(userRequest.getEmail());
            }
            if (Objects.nonNull(userRequest.getPassword())) {
                data.setPassword(userRequest.getPassword());
            }
            Request request = mgmt.users().update(authId, data);

            //Execute update
            request.execute();
        } catch (APIException ex) {
            log.error("[{}] {} During createAuth0User",
                    ErrorCodes.AUTH0_API_ERROR.getErrorCode(), ErrorCodes.AUTH0_API_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.AUTH0_API_ERROR);
        } catch (Auth0Exception ex) {
            log.error("[{}] {} During createAuth0User",
                    ErrorCodes.AUTH0_REQUEST_ERROR.getErrorCode(), ErrorCodes.AUTH0_REQUEST_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.AUTH0_REQUEST_ERROR);
        } catch (Exception ex) {
            log.error("[{}] {} During createAuth0User",
                    ErrorCodes.UNKNOWN_ERROR.getErrorCode(), ErrorCodes.UNKNOWN_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_ERROR);
        }
    }
}
