package org.jegdev.library.users.infrastructure.adapter.out.persistence.repository;

import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jegdev.library.users.domain.model.User;
import org.jegdev.library.users.domain.port.out.UserRepository;
import org.jegdev.library.users.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.jegdev.library.users.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;

import static com.mongodb.client.model.Filters.eq;

/**
 * Implementación del puerto de salida UserRepository.
 * Utiliza MongoDB reactivo para las operaciones de persistencia.
 * Implementa el patrón Adapter de la arquitectura hexagonal.
 */
@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {

    // Dependencias requeridas marcadas como final para garantizar inmutabilidad
    private final ReactiveMongoCollection<UserEntity> userCollection; // Colección reactiva de MongoDB para usuarios
    private final UserPersistenceMapper mapper; // Conversor entre objetos de dominio y entidades de persistencia

    /**
     * Constructor que inicializa el repositorio con sus dependencias requeridas.
     * Las dependencias se inyectan automáticamente mediante CDI.
     *
     * @param userCollection colección reactiva de MongoDB para usuarios
     * @param mapper conversor entre objetos de dominio y entidades de persistencia
     */
    @Inject
    public UserRepositoryImpl(ReactiveMongoCollection<UserEntity> userCollection, UserPersistenceMapper mapper) {
        this.userCollection = userCollection;
        this.mapper = mapper;
    }

    /**
     * Guarda un nuevo usuario en la base de datos.
     * Utiliza programación reactiva con Mutiny para operaciones asíncronas.
     *
     * @param user objeto del dominio a persistir
     * @return Uni<User> usuario persistido convertido a dominio
     */
    @Override
    public Uni<User> save(User user) {
        UserEntity userEntity = mapper.toEntity(user);
        return userCollection.insertOne(userEntity)
                .map(result -> mapper.toDomain(userEntity));
    }

    /**
     * Busca un usuario por su email en la base de datos.
     * Utiliza programación reactiva con Mutiny para operaciones asíncronas.
     *
     * @param email email del usuario a buscar
     * @return Uni<User> usuario encontrado convertido a dominio, o Uni null si no se encuentra
     */
    @Override
    public Uni<User> findByEmail(String email) {
        return userCollection.find(eq("email", email))
                .toUni()
                .map(mapper::toDomain);
    }
}
