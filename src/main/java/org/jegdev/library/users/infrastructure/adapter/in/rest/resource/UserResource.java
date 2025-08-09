package org.jegdev.library.users.infrastructure.adapter.in.rest.resource;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
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
import org.jegdev.library.users.domain.port.in.LoginUserUseCase;
import org.jegdev.library.users.domain.port.in.RegisterUserUseCase;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.LoginRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.LoginResponse;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserResponse;

@Path("/users")
@Tag(name = "Users", description = "Operaciones relacionadas con usuarios")
public class UserResource {

    private final RegisterUserUseCase registerUserUseCase; // Puerto de entrada para el registro de usuarios
    private final FindUserByEmailUseCase findUserByEmailUseCase; // Puerto de entrada para la búsqueda de usuarios por email
    private final LoginUserUseCase loginUserUseCase; // Puerto de entrada para el inicio de sesión de usuarios

    @Inject
    public UserResource(RegisterUserUseCase registerUserUseCase, FindUserByEmailUseCase findUserByEmailUseCase, LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.findUserByEmailUseCase = findUserByEmailUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userRequest DTO con la información del usuario a registrar
     * @return Respuesta HTTP con el usuario registrado
     */
    @POST
    @Path("/register")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
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
     * Inicia sesión de un usuario y devuelve un JWT.
     *
     * @param loginRequest DTO con email y contraseña
     * @return JWT si las credenciales son válidas
     */
    @POST
    @Path("/login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Login de usuario", description = "Autentica al usuario y retorna un JWT")
    @APIResponse(
            responseCode = "200",
            description = "Login exitoso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = LoginResponse.class))
    )
    @APIResponse(
            responseCode = "401",
            description = "Credenciales inválidas"
    )
    public Uni<Response> login(@Valid LoginRequest loginRequest) {
        return loginUserUseCase.login(loginRequest)
                .map(response -> Response.ok(response).build());
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email Email del usuario a buscar
     * @return Respuesta HTTP con el usuario encontrado o un error si no existe
     */
    @GET
    @Path("/getUser/{email}")
    @RolesAllowed("ADMIN") // Solo los administradores pueden buscar usuarios por email
    @Produces(MediaType.APPLICATION_JSON)
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
