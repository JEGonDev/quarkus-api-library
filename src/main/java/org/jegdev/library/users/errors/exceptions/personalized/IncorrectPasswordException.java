package org.jegdev.library.users.errors.exceptions.personalized;

import org.jegdev.library.users.errors.exceptions.base.UserException;

public class IncorrectPasswordException extends UserException {
    public IncorrectPasswordException(String email) {
        super("USER-003", String.format("La contraseña proporcionada es incorrecta para el usuario con email: %s", email));
    }
}
