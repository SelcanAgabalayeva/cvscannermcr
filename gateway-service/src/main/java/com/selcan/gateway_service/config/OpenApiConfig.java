package com.selcan.gateway_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservices Gateway API",
                version = "1.0",
                description = "Centralized API Gateway documentation for all backend microservices."
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "API Gateway")
        }
)
public class OpenApiConfig {

}
