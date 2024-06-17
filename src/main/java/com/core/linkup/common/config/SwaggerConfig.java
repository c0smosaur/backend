package com.core.linkup.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    private static final String SECURITY_SCHEME_NAME = "Authorization";
    private static final String SECURITY_SCHEME_REFRESH_NAME = "refresh-token";

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper);
    }

    @Bean
    public OpenAPI swaggerApi(){
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT"))
                        .addSecuritySchemes(SECURITY_SCHEME_REFRESH_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_REFRESH_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .in(SecurityScheme.In.HEADER)
                                .scheme("Bearer")
                                .bearerFormat("JWT"))
                )
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .info(new Info()
                        .title("패스트캠퍼스 기업연계 프로젝트-3조 LinkUp 백엔드")
                        .description("공유오피스 서비스 프로젝트"))
                .servers(List.of(
                        new Server().url("https://api.linkup3mw.com")))
                .path("/api/v1/member/logout", new PathItem()
                        .get(new Operation()
                                .operationId("logout")
                                .description("Log out the current user")
                                .addTagsItem("Authentication")
                                .addParametersItem(new Parameter()
                                        .in("header")
                                        .name("Logout")
                                        .description("Bearer token")
                                        .required(true)
                                        .schema(new StringSchema().format("string"))
                                        .example("Bearer <token>"))
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse()
                                                .description("Logout successful"))
                                        .addApiResponse("401", new ApiResponse()
                                                .description("Unauthorized")))));
    }
}
