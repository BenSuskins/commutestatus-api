package co.uk.suskins.commutestatus.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enabled)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                title,
                description,
                version,
                "",
                new Contact(name, website, email),
                license,
                licenseUrl,
                Collections.emptyList());
    }
}