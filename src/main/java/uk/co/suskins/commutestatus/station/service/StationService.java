package uk.co.suskins.commutestatus.station.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.suskins.commutestatus.common.models.entities.Station;
import uk.co.suskins.commutestatus.common.models.exceptions.CommuteStatusServiceException;
import uk.co.suskins.commutestatus.common.models.exceptions.ErrorCodes;
import uk.co.suskins.commutestatus.common.repository.StationRepository;
import uk.co.suskins.commutestatus.station.models.api.StationResponse;
import uk.co.suskins.commutestatus.station.models.mapper.StationMapper;

@Slf4j
@Service
public class StationService {
    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Autowired
    public StationService(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    public StationResponse getStations() {
        try {
            Iterable<Station> stations = stationRepository.findAll();
            return stationMapper.stationListToStationResponse(stations);
        } catch (CommuteStatusServiceException ex) {
            throw ex;
        } catch (DataException ex) {
            log.error("[{}] {} During getStations",
                    ErrorCodes.DATABASE_ERROR.getErrorCode(),
                    ErrorCodes.DATABASE_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.DATABASE_ERROR);
        } catch (Exception ex) {
            log.error("[{}] {} During getStations",
                    ErrorCodes.UNKNOWN_ERROR.getErrorCode(),
                    ErrorCodes.UNKNOWN_ERROR.getErrorTitle(), ex);
            throw new CommuteStatusServiceException(ErrorCodes.UNKNOWN_ERROR);
        }
    }
}
