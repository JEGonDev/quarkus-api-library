package org.jegdev.library.books.infrastructure.adapter.out.persistence.repository;


import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.jegdev.library.books.domain.model.Book;
import org.jegdev.library.books.domain.port.out.BookRepository;

@ApplicationScoped // Crea una instancia de la clase que dura todo el ciclo de vida de la app
public class BookRepositoryImpl implements BookRepository {

    @Override
    public Uni<Book> save(Book book) {
        return null;
    }
}
