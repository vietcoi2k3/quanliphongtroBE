package com.apec.pos.until;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(),
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local EVM",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD EVM",
                        url = "http://14.225.204.101:8080/"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        description = "JWT auth"
)
public class SwaggerConfig {
}
