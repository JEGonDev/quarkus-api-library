package org.jegdev.library.users.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserRequest", description = "Datos requeridos para el registro de un nuevo usuario")
public class UserRequest {

    // Identificador del usuario, se genera automaticamente desde MongoDB

    @Schema(description = "Nombre del usuario", example = "Juan Perez", required = true)
    @NotBlank(message = "El nombre del usuario no puede estar vacio.")
    private String name;

    @Schema(description = "Email del usuario", example = "juan.example@gmail.com@", required = true)
    @NotBlank(message = "El email del usuario no puede estar vacio.")
    private String email;

    @Schema(description = "Password del usuario", example = "P@ssw0rd", required = true)
    @NotBlank(message = "El password del usuario no puede estar vacio.")
    private String password;

}
