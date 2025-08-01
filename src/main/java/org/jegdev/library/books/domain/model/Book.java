package org.jegdev.library.books.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Esta clase representa el modelo de negocio de un libro.
// No debe tener anotaciones de Mongo, JSON ni validación externa.

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int stock;
}
