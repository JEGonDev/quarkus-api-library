package org.jegdev.library.users.errors.exceptions.personalized;

import org.jegdev.library.users.errors.exceptions.base.UserException;

public class UserDuplicateException extends UserException {
    public UserDuplicateException(String email) {
        super("USER-001", String.format("Ya existe un usuario con el email: %s", email));
    }
}
