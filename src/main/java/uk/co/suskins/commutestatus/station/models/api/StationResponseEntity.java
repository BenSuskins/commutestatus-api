package uk.co.suskins.commutestatus.station.models.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationResponseEntity {
    private Long id;
    private String name;
    private String crs;
}
