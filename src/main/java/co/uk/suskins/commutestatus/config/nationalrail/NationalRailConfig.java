package co.uk.suskins.commutestatus.config.nationalrail;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class NationalRailConfig {
    @Value("${NATIONAL_RAIL_ACCESS_TOKEN:}")
    private String accessToken;
}
