package uk.co.suskins.commutestatus.station.models.mapper;

import org.springframework.stereotype.Component;
import uk.co.suskins.commutestatus.common.models.entities.Station;
import uk.co.suskins.commutestatus.station.models.api.StationResponse;
import uk.co.suskins.commutestatus.station.models.api.StationResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class StationMapper {
    public StationResponse stationListToStationResponse(Iterable<Station> stations) {
        List<StationResponseEntity> stationEntities = new ArrayList<>();
        stations.forEach(station ->
                stationEntities.add(new StationResponseEntity(station.getId(), station.getName(), station.getCrs())));
        return new StationResponse(stationEntities);
    }
}
