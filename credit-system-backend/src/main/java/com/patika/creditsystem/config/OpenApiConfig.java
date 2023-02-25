package com.patika.creditsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public void creditSystemApi(){
        String description= "It receives loan application" +
                " requests for a loan application system" +
                " and shows the loan result to the " +
                " according to the relevant criteria";

       new OpenAPI()
                .info(new Info().title("Credit-System")
                        .description(description)
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("desc")
                        .url("http://localhost:8080/swagger-ui/index.html#/"));
    }
}
