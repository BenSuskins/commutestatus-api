package uk.co.suskins.commutestatus.commutestatus.models.mapper;

import com.thalesgroup.rtti._2017_10_01.ldb.types.StationBoardWithDetails;
import org.springframework.stereotype.Component;
import uk.co.suskins.commutestatus.commutestatus.models.api.CommuteStatusResponse;
import uk.co.suskins.commutestatus.commutestatus.models.api.CommuteStatusResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CommuteStatusMapper {
    public CommuteStatusResponse stationBoardToCommuteStatusResponse(StationBoardWithDetails stationBoardToWork,
                                                                     StationBoardWithDetails stationBoardToHome) {
        List<CommuteStatusResponseEntity> toWork = new ArrayList<>();
        List<CommuteStatusResponseEntity> toHome = new ArrayList<>();

        if (Objects.nonNull(stationBoardToWork.getTrainServices())) {
            toWork = stationBoardToWork.getTrainServices().getService().stream()
                    .map(serviceItem -> CommuteStatusResponseEntity.builder()
                            .estimatedTimeOfDeparture(serviceItem.getEtd())
                            .scheduledTimeOfDeparture(serviceItem.getStd())
                            .isCancelled(
                                    (Objects.isNull(serviceItem.isIsCancelled())
                                            ? false : serviceItem.isIsCancelled())
                                            || (Objects.isNull(serviceItem.isFilterLocationCancelled())
                                            ? false : serviceItem.isFilterLocationCancelled()))
                            .platform(Objects.isNull(serviceItem.getPlatform()) ? "" : serviceItem.getPlatform())
                            .cancelReason(Objects.isNull(serviceItem.getCancelReason()) ? "" : serviceItem.getCancelReason())
                            .delayReason(Objects.isNull(serviceItem.getDelayReason()) ? "" : serviceItem.getDelayReason())
                            .from(stationBoardToWork.getLocationName())
                            .to(stationBoardToWork.getFilterLocationName())
                            .build()).collect(Collectors.toList());
        }
        if (Objects.nonNull(stationBoardToHome.getTrainServices())) {
            toHome = stationBoardToHome.getTrainServices().getService().stream()
                    .map(serviceItem -> CommuteStatusResponseEntity.builder()
                            .estimatedTimeOfDeparture(serviceItem.getEtd())
                            .scheduledTimeOfDeparture(serviceItem.getStd())
                            .isCancelled(
                                    (Objects.isNull(serviceItem.isIsCancelled())
                                            ? false : serviceItem.isIsCancelled())
                                            || (Objects.isNull(serviceItem.isFilterLocationCancelled())
                                            ? false : serviceItem.isFilterLocationCancelled()))
                            .platform(Objects.isNull(serviceItem.getPlatform()) ? "" : serviceItem.getPlatform())
                            .cancelReason(Objects.isNull(serviceItem.getCancelReason()) ? "" : serviceItem.getCancelReason())
                            .delayReason(Objects.isNull(serviceItem.getDelayReason()) ? "" : serviceItem.getDelayReason())
                            .from(stationBoardToHome.getLocationName())
                            .to(stationBoardToHome.getFilterLocationName())
                            .build()).collect(Collectors.toList());
        }
        return new CommuteStatusResponse(toWork, toHome);
    }
}
