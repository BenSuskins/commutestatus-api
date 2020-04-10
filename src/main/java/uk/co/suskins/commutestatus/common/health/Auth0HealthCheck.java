package uk.co.suskins.commutestatus.common.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import uk.co.suskins.commutestatus.config.auth0.Auth0Config;

@Component
@Slf4j
public class Auth0HealthCheck implements HealthIndicator {
    public static final String AUTH0_TEST_URL = "/test";
    private final Auth0Config auth0Config;

    @Autowired
    public Auth0HealthCheck(Auth0Config auth0Config) {
        this.auth0Config = auth0Config;
    }

    @Override
    public Health health() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(auth0Config.getDomain() + AUTH0_TEST_URL,
                    HttpMethod.GET,
                    null,
                    String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return Health.up().build();
            } else {
                return Health.down().withDetail("Error with Auth0", response.getBody()).build();
            }
        } catch (ResourceAccessException ex) {
            return Health.down().withDetail("Error with Auth0 Test request", ex.getLocalizedMessage()).build();
        }
    }
}
