package org.jegdev.library.books.domain.port.in;

import io.smallrye.mutiny.Uni;
import org.jegdev.library.books.dto.BookRequest;
import org.jegdev.library.books.dto.BookResponse;

/**
 * Puerto de entrada para crear un libro.
 * Define la operación que puede invocar un adaptador de entrada (REST, eventos, etc).
 */
public interface CreateBookUseCase {
    Uni<BookResponse> create(BookRequest bookRequest);
}
