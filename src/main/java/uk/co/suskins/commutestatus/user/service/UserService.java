package uk.co.suskins.commutestatus.user.service;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.CreatedUser;
import com.auth0.net.SignUpRequest;
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
                    .authId("auth0|" + createAuth0User(userRequest))
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
            AuthAPI authAPI = new AuthAPI(auth0Config.getDomain(),
                    auth0Config.getClientId(), auth0Config.getClientSecret());
            SignUpRequest request =
                    authAPI.signUp(userRequest.getEmail(), userRequest.getPassword(), AUTH0_CONNECTION);
            CreatedUser user = request.execute();

            return user.getUserId();
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
