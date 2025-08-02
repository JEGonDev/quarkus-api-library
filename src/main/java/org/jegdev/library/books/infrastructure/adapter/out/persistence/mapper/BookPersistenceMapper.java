package org.jegdev.library.books.infrastructure.adapter.out.persistence.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;
import org.jegdev.library.books.domain.model.Book;
import org.jegdev.library.books.infrastructure.adapter.out.persistence.entity.BookEntity;

/**
 * Mapper encargado de convertir entre entidades de persistencia
 * y objetos del modelo de dominio (Book).
 */
@ApplicationScoped // Crea una instancia de la clase util por todo el ciclo de vida de la App
public class BookPersistenceMapper {

    /**
     * Convierte una entidad de persistencia a un objeto de dominio.
     *
     * @param entity entidad recuperada de la base de datos
     * @return instancia de Book para uso en el dominio
     */
    public Book toDomain(BookEntity entity) {
        return Book.builder()
                .id(entity.getId().toHexString())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .isbn(entity.getIsbn())
                .stock(entity.getStock())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    /**
     * Convierte un objeto de dominio a una entidad de persistencia.
     *
     * @param book objeto de dominio a persistir
     * @return entidad preparada para guardar en base de datos
     */
    public BookEntity toEntity(Book book) {
        return BookEntity.builder()
                .id(book.getId() != null ? new ObjectId(book.getId()) : null)
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .stock(book.getStock())
                .createdAt(book.getCreatedAt())
                .build();
    }
}
