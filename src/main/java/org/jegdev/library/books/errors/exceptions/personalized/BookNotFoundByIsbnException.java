package org.jegdev.library.books.errors.exceptions.personalized;

import org.jegdev.library.books.errors.exceptions.base.BookException;

public class BookNotFoundByIsbnException extends BookException {
    public BookNotFoundByIsbnException(String isbn) {
        super("BOOK-004", String.format("No se encontró el libro con ISBN: %s", isbn));
    }
}
