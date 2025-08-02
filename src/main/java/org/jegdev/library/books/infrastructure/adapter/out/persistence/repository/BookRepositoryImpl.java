package org.jegdev.library.books.infrastructure.adapter.out.persistence.repository;


import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jegdev.library.books.domain.model.Book;
import org.jegdev.library.books.domain.port.out.BookRepository;
import org.jegdev.library.books.infrastructure.adapter.out.persistence.entity.BookEntity;
import org.jegdev.library.books.infrastructure.adapter.out.persistence.mapper.BookPersistenceMapper;

/**
 * Implementación del puerto de salida BookRepository.
 * Utiliza MongoDB reactivo para las operaciones de persistencia.
 * Implementa el patrón Adapter de la arquitectura hexagonal.
 */
@ApplicationScoped // Crea una instancia de la clase que dura todo el ciclo de vida de la app
public class BookRepositoryImpl implements BookRepository {

    // Dependencias requeridas marcadas como final para garantizar inmutabilidad
    private final ReactiveMongoCollection<BookEntity> bookCollection; // Colección reactiva de MongoDB para operaciones de persistencia
    private final BookPersistenceMapper mapper; // Conversor entre objetos de dominio y entidades de persistencia

    /**
     * Constructor que inicializa el repositorio con sus dependencias requeridas.
     * Las dependencias se inyectan automáticamente mediante CDI.
     *
     * @param bookCollection colección reactiva de MongoDB para libros
     * @param mapper conversor entre objetos de dominio y entidades de persistencia
     */
    @Inject
    public BookRepositoryImpl(ReactiveMongoCollection<BookEntity> bookCollection, BookPersistenceMapper mapper) {
        this.bookCollection = bookCollection;
        this.mapper = mapper;
    }

    /**
     * Guarda un nuevo libro en la base de datos.
     * Utiliza programación reactiva con Mutiny para operaciones asíncronas.
     *
     * @param book objeto del dominio a persistir
     * @return Uni<Book> libro persistido convertido a dominio
     */
    @Override
    public Uni<Book> save(Book book) {
        BookEntity entity = mapper.toEntity(book);
        return bookCollection.insertOne(entity)
                .map(result -> mapper.toDomain(entity));
    }
}
