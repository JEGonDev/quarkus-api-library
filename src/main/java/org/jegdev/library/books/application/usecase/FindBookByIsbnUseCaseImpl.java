package org.jegdev.library.books.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jegdev.library.books.domain.port.in.FindBookByIsbnUseCase;
import org.jegdev.library.books.domain.port.out.BookRepository;
import org.jegdev.library.books.errors.exceptions.personalized.BookNotFoundByIsbnException;
import org.jegdev.library.books.infrastructure.adapter.in.rest.dto.BookResponse;
import org.jegdev.library.books.infrastructure.adapter.in.rest.mapper.BookDtoMapper;

/**
 * Implementación del caso de uso para encontrar un libro por su ISBN.
 * Esta clase maneja el flujo reactivo completo para la búsqueda de libros por ISBN.
 *
 * @ApplicationScoped garantiza una única instancia para toda la aplicación
 */
@ApplicationScoped
public class FindBookByIsbnUseCaseImpl implements FindBookByIsbnUseCase {

    // Dependencias requeridas marcadas como final para garantizar inmutabilidad
    private final BookRepository bookRepository; // Puerto de salida para persistencia
    private final BookDtoMapper mapper;   // Mapper para convertir entre DTO y dominio

    /**
     * Constructor con inyección de dependencias.
     * IMPORTANTE: El parámetro bookRepository debe ser la interfaz BookRepository, no BookRepositoryImpl
     * para mantener el principio de inversión de dependencias.
     */
    public FindBookByIsbnUseCaseImpl(BookRepository bookRepository, BookDtoMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    /**
     * Método principal que orquesta el flujo para encontrar un libro por su ISBN.
     *
     * @param isbn ISBN del libro a buscar
     * @return Uni<BookResponse> Respuesta reactiva con el libro encontrado
     */
    @Override
    public Uni<BookResponse> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .onItem().ifNull().failWith(() ->
                        new BookNotFoundByIsbnException(isbn)
                )
                .map(mapper::toResponse);
    }
}
