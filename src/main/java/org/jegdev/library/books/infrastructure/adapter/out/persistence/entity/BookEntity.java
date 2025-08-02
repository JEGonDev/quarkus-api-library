package org.jegdev.library.books.infrastructure.adapter.out.persistence.entity;

import com.arjuna.ats.internal.jta.recovery.arjunacore.XARecoveryModule;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MongoEntity(collection="books")
@BsonDiscriminator // Marca la clase con un tipo (discriminador) que Mongo puede usar para manejar jerarquía de clases (herencia).
public class BookEntity extends ReactivePanacheMongoEntity {
    @BsonId
    public ObjectId id;

    public String title;
    public String author;
    public String isbn;
    public int stock;
    public Instant createdAt;
}
