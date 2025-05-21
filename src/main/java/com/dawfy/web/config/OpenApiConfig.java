package com.dawfy.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI dawfyOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Dawfy API")
                                                .description("API para la aplicación de música Dawfy")
                                                .version("1.0")
                                                .contact(new Contact()
                                                                .name("Equipo Dawfy")
                                                                .email("contacto@dawfy.com"))
                                                .license(new License()
                                                                .name("Licencia API")
                                                                .url("https://www.dawfy.com/licencias")));
        }
}