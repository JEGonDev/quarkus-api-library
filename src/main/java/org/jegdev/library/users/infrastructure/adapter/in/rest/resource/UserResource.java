package org.jegdev.library.users.infrastructure.adapter.in.rest.resource;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jegdev.library.users.domain.port.in.FindUserByEmailUseCase;
import org.jegdev.library.users.domain.port.in.RegisterUserUseCase;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserResponse;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users", description = "Operaciones relacionadas con usuarios")
public class UserResource {

    private final RegisterUserUseCase registerUserUseCase; // Puerto de entrada para el registro de usuarios
    private final FindUserByEmailUseCase findUserByEmailUseCase; // Puerto de entrada para la búsqueda de usuarios por email

    @Inject
    public UserResource(RegisterUserUseCase registerUserUseCase, FindUserByEmailUseCase findUserByEmailUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.findUserByEmailUseCase = findUserByEmailUseCase;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userRequest DTO con la información del usuario a registrar
     * @return Respuesta HTTP con el usuario registrado
     */
    @POST
    @Path("/register")
    @Operation(summary = "Registrar un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @APIResponse(
            responseCode = "201",
            description = "Usuario registrado exitosamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = UserResponse.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos"
    )
    public Uni<Response> registerUser(@Valid UserRequest userRequest) {
        return registerUserUseCase.register(userRequest)
                .map(userResponse -> Response.status(Response.Status.CREATED)
                        .entity(userResponse)
                        .build());
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar
     * @return Respuesta HTTP con el usuario encontrado o un error si no existe
     */
    @POST
    @Path("/findByEmail/{email}")
    @Operation(summary = "Buscar usuario por email", description = "Busca un usuario por su email")
    @APIResponse(
            responseCode = "200",
            description = "Usuario encontrado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = UserResponse.class))
    )
    @APIResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
    )
    public Uni<Response> findUserByEmail(@PathParam("email") String email) {
        return findUserByEmailUseCase.findUserByEmail(email)
                .map(userResponse -> Response.ok(userResponse).build());
    }
}
