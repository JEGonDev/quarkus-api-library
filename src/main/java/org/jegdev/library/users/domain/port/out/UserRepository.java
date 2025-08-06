package org.jegdev.library.users.domain.port.out;

import io.smallrye.mutiny.Uni;
import org.jegdev.library.users.domain.model.User;

public interface UserRepository {
    Uni<User> save(User user); // Guarda un nuevo usuario en la base de datos
}
