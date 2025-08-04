package org.jegdev.library.books.errors.exceptions.base;

import org.jegdev.library.shared.errors.ApiException;

/**
 * Excepción base para todas las excepciones relacionadas con libros.
 * Extiende de ApiException para mantener la consistencia en el manejo de errores.
 */
public abstract class BookException extends ApiException {

    protected BookException(String code, String message) {
        super(code, message);
    }
}
