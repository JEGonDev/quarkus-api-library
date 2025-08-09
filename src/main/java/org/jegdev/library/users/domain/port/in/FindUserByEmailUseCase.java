package org.jegdev.library.users.domain.port.in;

import io.smallrye.mutiny.Uni;
import org.jegdev.library.users.infrastructure.adapter.in.rest.dto.UserResponse;

public interface FindUserByEmailUseCase {
    Uni<UserResponse> findUserByEmail(String email);
}
