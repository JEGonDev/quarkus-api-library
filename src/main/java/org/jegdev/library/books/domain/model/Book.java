package org.jegdev.library.books.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

// Esta clase representa el modelo de negocio de un libro.
// No debe tener anotaciones de Mongo, JSON ni validación externa.

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int stock;
    private Instant createdAt;
}
