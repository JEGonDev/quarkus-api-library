package org.jegdev.library.books.errors.exceptions.personalized;

import org.jegdev.library.books.errors.exceptions.base.BookException;

/**
 * Excepción lanzada cuando se intenta crear un libro con un ISBN que ya existe
 */
public class BookDuplicateException extends BookException {
    public BookDuplicateException(String isbn) {
        super("BOOK-001", String.format("Ya existe un libro con el ISBN: %s", isbn));
    }
}
