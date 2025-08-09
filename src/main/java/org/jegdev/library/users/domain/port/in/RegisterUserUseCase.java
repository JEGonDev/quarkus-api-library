package org.jegdev.library.users.domain.port.in;

import io.smallrye.mutiny.Uni;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserResponse;

/**
 * Puerto de entrada para registrar un usuario.
 * Define la operación que puede invocar un adaptador de entrada (REST, eventos, etc).
 */
public interface RegisterUserUseCase {
    Uni<UserResponse> register(UserRequest userRequest);
}
