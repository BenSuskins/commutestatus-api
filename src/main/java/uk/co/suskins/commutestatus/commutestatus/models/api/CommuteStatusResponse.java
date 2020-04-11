package uk.co.suskins.commutestatus.commutestatus.models.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommuteStatusResponse {
    List<CommuteStatusResponseEntity> toWork;
    List<CommuteStatusResponseEntity> toHome;
}
