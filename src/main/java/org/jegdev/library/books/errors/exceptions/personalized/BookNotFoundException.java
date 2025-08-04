package org.jegdev.library.books.errors.exceptions.personalized;

import org.jegdev.library.books.errors.exceptions.base.BookException;

public class BookNotFoundException extends BookException {
    public BookNotFoundException(String id) {
        super("BOOK-003", String.format("No se encontró el libro con ID: %s", id));
    }
}
