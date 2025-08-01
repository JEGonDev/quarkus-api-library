package org.jegdev.library.books.domain.port.in;

import io.smallrye.mutiny.Uni;
import org.jegdev.library.books.domain.model.Book;
import org.jegdev.library.books.dto.BookRequest;

/**
 * Puerto de entrada para crear un libro.
 * Define la operación que puede invocar un adaptador de entrada (REST, eventos, etc).
 */
public interface CreateBookUseCase {
    Uni<Book> create(BookRequest bookRequest);
}
