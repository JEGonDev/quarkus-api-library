package org.jegdev.library.users.infrastructure.adapter.out.security;

import jakarta.enterprise.context.ApplicationScoped;
import org.jegdev.library.users.domain.port.out.PasswordHasher;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class BCryptPasswordHasher implements PasswordHasher {

    @Override
    public String hash(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean verify(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
