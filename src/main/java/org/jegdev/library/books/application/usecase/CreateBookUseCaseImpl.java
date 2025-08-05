package org.jegdev.library.books.application.usecase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jegdev.library.books.domain.model.Book;
import org.jegdev.library.books.domain.port.in.CreateBookUseCase;
import org.jegdev.library.books.domain.port.out.BookRepository;
import org.jegdev.library.books.errors.exceptions.personalized.BookDuplicateException;
import org.jegdev.library.books.infrastructure.adapter.in.rest.dto.BookRequest;
import org.jegdev.library.books.infrastructure.adapter.in.rest.dto.BookResponse;
import org.jegdev.library.books.infrastructure.adapter.in.rest.mapper.BookDtoMapper;
import org.jegdev.library.books.infrastructure.adapter.out.persistence.repository.BookRepositoryImpl;

import java.time.Instant;

/**
 * Implementación del caso de uso para crear libros.
 * Esta clase maneja el flujo reactivo completo para la creación de libros,
 * incluyendo validaciones y persistencia.
 *
 * @ApplicationScoped garantiza una única instancia para toda la aplicación
 */
@ApplicationScoped
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
    public CreateBookUseCaseImpl(BookRepository bookRepository, BookDtoMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    /**
     * Método principal que orquesta el flujo de creación de un libro.
     * El flujo sigue estos pasos:
     * 1. Valida que no exista un libro con el mismo ISBN
     * 2. Crea una entidad de dominio Book a partir del DTO
     * 3. Persiste el libro en la base de datos
     * 4. Convierte y retorna la respuesta
     *
     * @param bookRequest DTO con los datos del libro a crear (ya validado por Bean Validation)
     * @return Uni<BookResponse> Respuesta reactiva con el libro creado
     * @throws BookDuplicateException si ya existe un libro con el mismo ISBN
     */
    @Override
    public Uni<BookResponse> create(BookRequest bookRequest) {
        return validateIsbnNotExists(bookRequest.getIsbn())  // paso 1: Validar el ISBN
                .map(ignored -> createBookFromRequest(bookRequest))  // paso 2: Creacion entidad
                .chain(this::saveBook)                      // paso 3 : Persistirlo
                .map(mapper::toResponse);                   // paso 4 : Convertirlo a respuesta valida
    }

    /**
     * Valida que no exista un libro con el ISBN proporcionado.
     * Flujo:
     * 1. Busca libro por ISBN
     * 2. Si encuentra algo (ifNotNull), lanza excepción
     * 3. Si no encuentra nada, continúa el flujo
     *
     * @param isbn ISBN a validar
     * @return Uni<Void> completado si no existe duplicado
     * @throws BookDuplicateException si el ISBN ya existe
     */
    private Uni<Void> validateIsbnNotExists(String isbn) {
        return bookRepository.findByIsbn(isbn) // paso 1: Buscar por ISBN
                .onItem().ifNotNull() // paso 2: Si encuentra algo
                .failWith(() -> new BookDuplicateException(isbn)) // lanza excepción
                .replaceWithVoid(); // paso 3: Si no encuentra nada, continúa el flujo
    }

    /**
     * Crea una nueva entidad Book a partir del DTO de request.
     * Establece la fecha de creación al momento actual.
     *
     * @param bookRequest DTO con los datos del libro
     * @return Book entidad de dominio creada
     */
    private Book createBookFromRequest(BookRequest bookRequest) {
        Book book = mapper.toDomain(bookRequest); // Usamos el mapper para convertir el DTO a entidad de dominio
        book.setCreatedAt(Instant.now()); // Establecemos la fecha de creación
        return book; // Retornamos la entidad creada
    }

    /**
     * Persiste la entidad Book en el repositorio.
     *
     * @param book Entidad a persistir
     * @return Uni<Book> Libro persistido
     */
    private Uni<Book> saveBook(Book book) {
        return bookRepository.save(book); // Persistimos usando el puerto de salida
    }
}
