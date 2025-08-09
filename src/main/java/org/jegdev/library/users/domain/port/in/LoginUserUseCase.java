package org.jegdev.library.users.domain.port.in;

import io.smallrye.mutiny.Uni;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.LoginRequest;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.LoginResponse;

public interface LoginUserUseCase {
    Uni<LoginResponse> login(LoginRequest loginRequest);
}
