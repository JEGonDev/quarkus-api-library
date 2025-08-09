package org.jegdev.library.users.errors.exceptions.personalized;

import org.jegdev.library.users.errors.exceptions.base.UserException;

public class UserEmailNotFoundException extends UserException {
    public UserEmailNotFoundException(String email) {
        super("USER-002", String.format("No se encontró un usuario con el email: %s", email));
    }
}
