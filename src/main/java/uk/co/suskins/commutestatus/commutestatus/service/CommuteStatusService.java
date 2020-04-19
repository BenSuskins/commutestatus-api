package uk.co.suskins.commutestatus.commutestatus.service;

import com.thalesgroup.rtti._2007_10_10.ldb.commontypes.FilterType;
import com.thalesgroup.rtti._2013_11_28.token.types.AccessToken;
import com.thalesgroup.rtti._2017_10_01.ldb.GetBoardRequestParams;
import com.thalesgroup.rtti._2017_10_01.ldb.LDBServiceSoap;
import com.thalesgroup.rtti._2017_10_01.ldb.Ldb;
import com.thalesgroup.rtti._2017_10_01.ldb.StationBoardWithDetailsResponseType;
import com.thalesgroup.rtti._2017_10_01.ldb.types.StationBoardWithDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.suskins.commutestatus.common.models.entities.User;
import uk.co.suskins.commutestatus.common.models.entities.UserPreference;
import uk.co.suskins.commutestatus.common.models.exceptions.CommuteStatusServiceException;
import uk.co.suskins.commutestatus.common.models.exceptions.ErrorCodes;
import uk.co.suskins.commutestatus.common.repository.UserPreferenceRepository;
import uk.co.suskins.commutestatus.common.repository.UserRepository;
import uk.co.suskins.commutestatus.commutestatus.models.api.CommuteStatusResponse;
import uk.co.suskins.commutestatus.commutestatus.models.mapper.CommuteStatusMapper;
import uk.co.suskins.commutestatus.config.nationalrail.NationalRailConfig;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
public class CommuteStatusService {
    private static final Integer NUMBER_ROWS = 5;
    private final UserPreferenceRepository userPreferenceRepository;
    private final UserRepository userRepository;
    private final AccessToken accessToken;
    private final CommuteStatusMapper commuteStatusMapper;
    private final LDBServiceSoap ldbServiceSoap;

    @Autowired
    public CommuteStatusService(UserPreferenceRepository userPreferenceRepository,
                                UserRepository userRepository,
                                NationalRailConfig nationalRailConfig,
                                CommuteStatusMapper commuteStatusMapper) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.userRepository = userRepository;
        this.accessToken = new AccessToken();
        this.accessToken.setTokenValue(nationalRailConfig.getAccessToken());
        this.commuteStatusMapper = commuteStatusMapper;
        Ldb ldb = new Ldb();
        this.ldbServiceSoap = ldb.getLDBServiceSoap12();
    }

    public CommuteStatusResponse getCommuteStatus(Principal principal) {
        try {
            Optional<User> optionalUser = userRepository.findByAuthId(principal.getName());
            if (optionalUser.isEmpty()) {
                log.error("[{}] {} During getCommuteStatus for auth ID {}",
                        ErrorCodes.USER_NOT_FOUND.getErrorCode(),
                        ErrorCodes.USER_NOT_FOUND.getErrorTitle(), principal.getName());
                throw new CommuteStatusServiceException(ErrorCodes.USER_NOT_FOUND);
            }

            User user = optionalUser.get();
            Optional<UserPreference> optionalUserPreference = userPreferenceRepository.findByUserId(user.getId());

            if (optionalUserPreference.isEmpty()) {
                log.error("[{}] {} During getCommuteStatus for auth ID {}",
                        ErrorCodes.USER_NOT_FOUND.getErrorCode(),
                        ErrorCodes.USER_NOT_FOUND.getErrorTitle(), principal.getName());
                throw new CommuteStatusServiceException(ErrorCodes.USER_NOT_FOUND);
            }
            UserPreference userPreference = optionalUserPreference.get();

            StationBoardWithDetails stationBoardToWork = getStationBoard(userPreference.getHomeStation().getCrs(),
                    userPreference.getWorkStation().getCrs());

            StationBoardWithDetails stationBoardToHome = getStationBoard(userPreference.getWorkStation().getCrs(),
                    userPreference.getHomeStation().getCrs());

            return commuteStatusMapper.stationBoardToCommuteStatusResponse(stationBoardToWork, stationBoardToHome);
        } catch (CommuteStatusServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("[{}] {} During getCommuteStatus",
                    ErrorCodes.UNKNOWN_ERROR.getErrorCode(), ErrorCodes.UNKNOWN_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_ERROR);
        }
    }

    private StationBoardWithDetails getStationBoard(String fromCrs, String toCrs) {
        try {
            GetBoardRequestParams params = new GetBoardRequestParams();
            params.setNumRows(NUMBER_ROWS);
            params.setCrs(fromCrs);
            params.setFilterCrs(toCrs);
            params.setFilterType(FilterType.TO);
            params.setTimeOffset(0);
            params.setTimeWindow(0);

            StationBoardWithDetailsResponseType departureBoard =
                    ldbServiceSoap.getDepBoardWithDetails(params, accessToken);
            return departureBoard.getGetStationBoardResult();
        } catch (Exception ex) {
            log.error("[{}] {} During getStationBoard",
                    ErrorCodes.NATIONAL_RAIL_ERROR.getErrorCode(), ErrorCodes.NATIONAL_RAIL_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.NATIONAL_RAIL_ERROR);
        }
    }
}

