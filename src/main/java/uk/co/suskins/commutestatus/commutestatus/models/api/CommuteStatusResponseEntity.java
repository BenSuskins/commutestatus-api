package uk.co.suskins.commutestatus.commutestatus.models.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommuteStatusResponseEntity {
    private String scheduledTimeOfDeparture;
    private String estimatedTimeOfDeparture;
    private String platform;
    private String delayReason;
    private String cancelReason;
    private Boolean isCancelled;
    private String to;
    private String from;
}
