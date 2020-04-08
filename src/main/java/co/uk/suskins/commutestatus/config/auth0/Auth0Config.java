package co.uk.suskins.commutestatus.config.auth0;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Auth0Config {
    @Value("${AUTH0_CLIENT_ID:}")
    private String auth0ClientId;
    @Value("${AUTH0_CLIENT_SECRET:}")
    private String auth0ClientSecret;
    @Value("${AUTH0_REFRESH_SECRET:}")
    private String auth0RefreshSecret;
    @Value("${AUTH0_DOMAIN:}")
    private String auth0Domain;
}
