package com.fran.ticketing_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ticketing API")
                        .version("0.0.1")
                        .description("API REST para gestión de tickets - documentación automática (OpenAPI/Swagger)")

                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project README")
                        .url("https://github.com/fran3103/ticketing-api")
                );
    }
}

