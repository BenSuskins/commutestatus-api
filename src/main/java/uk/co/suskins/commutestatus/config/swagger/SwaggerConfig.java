package uk.co.suskins.commutestatus.config.swagger;

import com.google.common.base.Predicate;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Data
@Configuration
@EnableSwagger2
@ConfigurationProperties(prefix = "base.swagger")
public class SwaggerConfig {
    private Boolean enabled;
    private String title;
    private String description;
    private String version;
    private String name;
    private String website;
    private String email;
    private String license;
    private String licenseUrl;
    private String tosUrl;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .enable(enabled)
                .apiInfo(apiInfo())
                .select()
                .apis(this.apis())
                .paths(this.paths())
                .build();
    }

    private Predicate<String> paths() {
        return PathSelectors.any();
    }

    private Predicate<RequestHandler> apis() {
        return RequestHandlerSelectors.basePackage("uk.co.suskins");
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                title,
                description,
                version,
                tosUrl,
                new Contact(name, website, email),
                license,
                licenseUrl,
                Collections.emptyList());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.regex("/api/v1/secure/.*")).build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }
}