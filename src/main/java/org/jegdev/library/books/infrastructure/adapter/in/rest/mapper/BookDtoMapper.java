package org.jegdev.library.books.infrastructure.adapter.in.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.jegdev.library.books.domain.model.Book;
import org.jegdev.library.books.dto.BookRequest;
import org.jegdev.library.books.dto.BookResponse;

/**
 * Mapper encargado de convertir entre objetos DTO (entrada/salida)
 * y objetos del modelo de dominio (Book).
 */
@ApplicationScoped // Se crea una unica instancia para todo el ciclo de vida de la App
public class BookDtoMapper {

    /**
     * Convierte un BookRequest (DTO de entrada) a un Book (modelo de dominio).
     *
     * @param request objeto recibido desde el cliente
     * @return instancia de Book para uso en el dominio
     */
    public Book toDomain(BookRequest request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .stock(request.getStock())
                .build();
    }

    /**
     * Convierte un Book (modelo de dominio) a un BookResponse (DTO de salida).
     *
     * @param book objeto de dominio procesado internamente
     * @return respuesta formateada para enviar al cliente
     */
    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .stock(book.getStock())
                .createdAt(book.getCreatedAt())
                .build();
    }
}
