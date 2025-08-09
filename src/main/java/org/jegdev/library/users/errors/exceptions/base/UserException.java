package org.jegdev.library.users.errors.exceptions.base;

import org.jegdev.library.shared.errors.ApiException;

/**
 * Excepción base para todas las excepciones relacionadas con usuarios.
 * Extiende de ApiException para mantener la consistencia en el manejo de errores.
 */
public abstract class UserException extends ApiException {
    protected UserException(String code, String message) {
        super(code, message);
    }
}
