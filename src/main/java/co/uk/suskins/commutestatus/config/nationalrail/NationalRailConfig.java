package co.uk.suskins.commutestatus.config.nationalrail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "nationalrail")
public class NationalRailConfig {
    private String accessToken;
}
