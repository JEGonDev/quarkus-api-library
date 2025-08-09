package org.jegdev.library.users.infrastructure.adapter.out.persistence.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.*;

import java.time.Instant;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MongoEntity
public class UserEntity extends ReactivePanacheMongoEntity {
    public String name;
    public String email;
    public String password;
    public List<String> roles; // Lista de roles del usuario
    public Instant createdAt;
}
