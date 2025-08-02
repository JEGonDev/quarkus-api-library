package org.jegdev.library.books.infrastructure.adapter.out.persistence.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MongoEntity(collection="books")
public class BookEntity extends ReactivePanacheMongoEntity {

    public String title;
    public String author;
    public String isbn;
    public int stock;
    public Instant createdAt;
}
