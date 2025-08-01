package org.jegdev.library.books.application.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jegdev.library.books.domain.model.Book;
import org.jegdev.library.books.domain.port.in.CreateBookUseCase;
import org.jegdev.library.books.domain.port.out.BookRepository;
import org.jegdev.library.books.dto.BookRequest;

@ApplicationScoped // Crea una unica instancia de esta clase durante todo el ciclo de vida de la app
public class CreateBookService implements CreateBookUseCase {

    @Inject
    BookRepository bookRepository;

    @Inject
    BookDtoMapper bookDtoMapper;

    @Override
    public Uni<Book> create(BookRequest bookRequest) {
        return null;
    }
}
