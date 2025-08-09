package org.jegdev.library.users.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Schema(name = "LoginRequest", description = "Datos requeridos para el inicio de sesión de un usuario")
public class LoginRequest {

    @Schema(description = "Email del usuario", example = "juanito@example.com", required = true)
    @NotBlank(message = "El email del usuario no puede estar vacio.")
    private String email;

    @Schema(description = "Password del usuario", example = "P@ssw0rd", required = true)
    @NotBlank(message = "El password del usuario no puede estar vacio.")
    private String password;
}
