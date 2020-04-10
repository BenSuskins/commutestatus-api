package uk.co.suskins.commutestatus.config.auth0;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "auth0")
public class Auth0Config {
    private String clientId;
    private String clientSecret;
    private String refreshSecret;
    private String domain;
}
