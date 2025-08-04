package org.jegdev.library.books.errors.exceptions.personalized;

import org.jegdev.library.books.errors.exceptions.base.BookException;

public class InvalidBookDataException extends BookException {
    public InvalidBookDataException(String message) {
        super("BOOK-002", message);
    }
}
