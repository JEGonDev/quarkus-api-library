package org.jegdev.library.books.domain.port.in;

import io.smallrye.mutiny.Uni;
import org.jegdev.library.books.infrastructure.adapter.in.rest.dto.BookResponse;

public interface FindBookByIsbnUseCase {
    Uni<BookResponse> findByIsbn (String isbn);
}
