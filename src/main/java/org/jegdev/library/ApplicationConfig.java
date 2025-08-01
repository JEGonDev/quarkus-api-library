package org.jegdev.library;

import jakarta.ws.rs.ApplicationPath;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Libreria",
                version = "1.0.0",
                description = "Ejemplo de API RESTful documentada con OpenAPI , hecha con arquitectura hexagonal, MongoDB reactivo y Quarkus"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Servidor local de desarrollo")
        }
)
@ApplicationPath("/library-api")
public class ApplicationConfig {
}
