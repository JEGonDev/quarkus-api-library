package org.jegdev.library.books.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jegdev.library.books.domain.model.Book;
import org.jegdev.library.books.domain.port.in.CreateBookUseCase;
import org.jegdev.library.books.domain.port.out.BookRepository;
import org.jegdev.library.books.infrastructure.adapter.in.rest.dto.BookRequest;
import org.jegdev.library.books.infrastructure.adapter.in.rest.dto.BookResponse;
import org.jegdev.library.books.infrastructure.adapter.in.rest.mapper.BookDtoMapper;

import java.time.Instant;

/**
 * Servicio que implementa el caso de uso para crear un libro.
 */
@ApplicationScoped // Crea una unica instancia de esta clase durante todo el ciclo de vida de la app
public class CreateBookUseCaseImpl implements CreateBookUseCase {

    @Inject
    BookRepository bookRepository;

    @Inject
    BookDtoMapper bookDtoMapper;

    @Override
    public Uni<BookResponse> create(BookRequest bookRequest) {
        // Convertimos el DTO recibido en un objeto del modelo de dominio
        Book book = bookDtoMapper.toDomain(bookRequest);
        book.setCreatedAt(Instant.now()); // Asignamos la fecha de creación

        // Guardamos el libro en el repositorio y transformamos el resultado en una respuesta
        return bookRepository.save(book)
                .map(bookDtoMapper::toResponse); // Convertimos el Book del dominio a BookResponse para el cliente
    }
}
