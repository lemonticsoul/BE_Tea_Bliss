package store.teabliss.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@OpenAPIDefinition(servers = {
        @Server(url = "https://teabliss.kro.kr", description = "Tea-Bliss HTTPS DEV SERVER"),
        @Server(url = "http://3.34.226.82:8080", description = "Tea-Bliss HTTP DEV SERVER"),
        @Server(url = "http://localhost:8080", description = "Tea-Bliss LOCAL SERVER")
})
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi pageApi() {
        String[] paths = {
                "/api/member/**",
                "/api/tea/**",
                "/api/ingredient/**",
                "/api/survey/**"
        };

        return GroupedOpenApi.builder()
                .group("Tea")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        List<Tag> tagList = List.of(
                createTag("회원 API", "회원 관련 API"),
                createTag("차 완제품 API", "차 완제품 관련 API"),
                createTag("차 재료 API", "차 재료 관련 API"),
                createTag("설문조사 API", "설문조사 관련 API")
        );

        String key = "Access Token (Bearer)";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(key);

        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        Components components = new Components()
                .addSecuritySchemes(key, accessTokenSecurityScheme);

        return new OpenAPI()
                .info(
                new Info().title("TEA-BLISS")
                        .description("TEA-BLISS Swagger 페이지")
                        .version("v1"))
                .tags(tagList)
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Tag createTag(String name, String description) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setDescription(description);
        return tag;
    }

}
