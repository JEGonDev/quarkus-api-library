package org.jegdev.library.shared.config;

import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jegdev.library.books.infrastructure.adapter.out.persistence.entity.BookEntity;
import org.jegdev.library.users.infrastructure.adapter.out.persistence.entity.UserEntity;

/**
 * Configuración centralizada para MongoDB.
 * Esta clase proporciona la configuración y los beans necesarios para la conexión
 * reactiva a MongoDB, siguiendo el patrón de inversión de control.
 *
 * @ApplicationScoped garantiza una única instancia durante el ciclo de vida de la aplicación
 */
@ApplicationScoped
public class MongoDbConfig {

    /**
     * Nombre de la base de datos obtenido de la configuración.
     * Se puede sobrescribir mediante la propiedad quarkus.mongodb.database
     * o la variable de entorno MONGO_DATABASE
     */
    @ConfigProperty(name = "quarkus.mongodb.database")
    String databaseName;

    /**
     * Nombres de las colecciones obtenidos de la configuración.
     * Pueden ser sobrescritos mediante propiedades o variables de entorno.
     */
    @ConfigProperty(name = "mongodb.collection.books")
    String booksCollection;

    @ConfigProperty(name = "mongodb.collection.users")
    String usersCollection;

    @ConfigProperty(name = "mongodb.collection.orders")
    String ordersCollection;

    @ConfigProperty(name = "mongodb.collection.deliverys")
    String deliverysCollection;

    /**
     * Produce el bean de la base de datos reactiva de MongoDB.
     * Este bean será inyectado donde se necesite una instancia de ReactiveMongoDatabase.
     *
     * @param client Cliente reactivo de MongoDB inyectado automáticamente
     * @return Instancia de la base de datos reactiva
     */
    @Produces
    @ApplicationScoped
    public ReactiveMongoDatabase mongoDatabase(ReactiveMongoClient client) {
        return client.getDatabase(databaseName);
    }

    /**
     * Produce la colección reactiva para Books.
     * La colección está tipada con BookEntity para garantizar type-safety.
     *
     * @param database Base de datos reactiva inyectada
     * @return Colección reactiva para Books
     */
    @Produces //Indica que este método es un productor de beans, es decir, crea y proporciona instancias de ReactiveMongoDatabase que pueden ser inyectadas en otras partes de la aplicación.
    @ApplicationScoped
    public ReactiveMongoCollection<BookEntity> bookCollection(ReactiveMongoDatabase database) {
        return database.getCollection(booksCollection, BookEntity.class);
    }

    /**
     * Produce la colección reactiva para Users.
     * La colección está tipada con UserEntity para garantizar type-safety.
     *
     * @param database Base de datos reactiva inyectada
     * @return Colección reactiva para Users
     */
    @Produces // Indica que este método es un productor de beans, es decir, crea y proporciona instancias de ReactiveMongoDatabase que pueden ser inyectadas en otras partes de la aplicación.
    @ApplicationScoped
    public ReactiveMongoCollection<UserEntity> userCollection(ReactiveMongoDatabase database) {
        return database.getCollection(usersCollection, UserEntity.class);
    }

    /**
     * Produce la colección reactiva para Orders.
     * La colección está tipada con OrderEntity para garantizar type-safety.
     *
     * @param database Base de datos reactiva inyectada
     * @return Colección reactiva para Orders
     */
//    @Produces // Indica que este método es un productor de beans, es decir, crea y proporciona instancias de ReactiveMongoDatabase que pueden ser inyectadas en otras partes de la aplicación.
//    @ApplicationScoped
//    public ReactiveMongoCollection<OrderEntity> orderCollection(ReactiveMongoDatabase database) {
//        return database.getCollection(ordersCollection, OrderEntity.class);
//    }

    /**
     * Produce la colección reactiva para Deliverys.
     * La colección está tipada con DeliveryEntity para garantizar type-safety.
     *
     * @param database Base de datos reactiva inyectada
     * @return Colección reactiva para Deliverys
     */
//    @Produces // Indica que este método es un productor de beans, es decir, crea y proporciona instancias de ReactiveMongoDatabase que pueden ser inyectadas en otras partes de la aplicación.
//    @ApplicationScoped
//    public ReactiveMongoCollection<DeliveryEntity> deliveryCollection(ReactiveMongoDatabase database) {
//        return database.getCollection(deliverysCollection, DeliveryEntity.class);
//    }
}