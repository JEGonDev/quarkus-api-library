package org.jegdev.library.books.domain.port.out;

import io.smallrye.mutiny.Uni;
import org.jegdev.library.books.domain.model.Book;

/**
 * Puerto de salida para persistencia de libros.
 * Define las operaciones que el dominio necesita del exterior.
 */
public interface BookRepository {
    Uni<Book> save(Book book);
}
