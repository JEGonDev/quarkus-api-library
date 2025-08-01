package org.jegdev.library.books.infrastructure.adapter.out.persistence;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@BsonDiscriminator // Marca la clase con un tipo (discriminador) que Mongo puede usar para manejar jerarquía de clases (herencia).
public class BookEntity extends PanacheMongoEntity {
    @BsonId
    public ObjectId id;

    public String title;
    public String author;
    public String isbn;
    public int stock;
    public Instant createdAt;
}
