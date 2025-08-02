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
import org.jegdev.library.books.infrastructure.adapter.out.persistence.repository.BookRepositoryImpl;

import java.time.Instant;

/**
 * Implementación del caso de uso para crear libros.
 * Actúa como un servicio de aplicación que coordina la lógica entre la capa de presentación y dominio.
 */
@ApplicationScoped // Crea una unica instancia de esta clase durante todo el ciclo de vida de la app
public class CreateBookUseCaseImpl implements CreateBookUseCase {

    // Dependencias requeridas marcadas como final para garantizar inmutabilidad
    private final BookRepository bookRepository;  // Puerto de salida para persistencia
    private final BookDtoMapper mapper;   // Mapper para convertir entre DTO y dominio

    /**
     * Constructor con inyección de dependencias.
     * IMPORTANTE: El parámetro bookRepository debe ser la interfaz BookRepository, no BookRepositoryImpl
     * para mantener el principio de inversión de dependencias.
     */
    @Inject
    public CreateBookUseCaseImpl(BookRepositoryImpl bookRepository, BookDtoMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    /**
     * Implementa la lógica del caso de uso para crear un nuevo libro.
     *
     * @param bookRequest DTO con los datos del libro a crear
     * @return Uni<BookResponse> respuesta reactiva con el libro creado
     */
    @Override
    public Uni<BookResponse> create(BookRequest bookRequest) {
        // Paso 1: Convertir el DTO a objeto de dominio
        Book book = mapper.toDomain(bookRequest);
        book.setCreatedAt(Instant.now());

        // Paso 2: Persistir el libro y transformar la respuesta
        return bookRepository.save(book)
                .map(mapper::toResponse);
    }
}
