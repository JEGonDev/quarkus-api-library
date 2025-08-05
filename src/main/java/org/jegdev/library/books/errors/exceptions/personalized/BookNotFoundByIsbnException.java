package org.jegdev.library.books.errors.exceptions.personalized;

import org.jegdev.library.shared.errors.ApiException;

public class BookNotFoundByIsbnException extends ApiException {
    public BookNotFoundByIsbnException(String isbn) {
        super("BOOK-004", String.format("No se encontró el libro con ISBN: %s", isbn));
    }
}
