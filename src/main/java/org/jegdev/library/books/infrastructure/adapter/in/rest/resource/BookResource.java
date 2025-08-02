package org.jegdev.library.books.infrastructure.adapter.in.rest.resource;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jegdev.library.books.domain.port.in.CreateBookUseCase;
import org.jegdev.library.books.infrastructure.adapter.in.rest.dto.BookRequest;
import org.jegdev.library.books.infrastructure.adapter.in.rest.dto.BookResponse;

/**
 * Controlador REST para operaciones con libros.
 * Implementa el adaptador primario (puerto de entrada) en la arquitectura hexagonal.
 */
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Books", description = "Operaciones relacionadas con libros")
public class BookResource {

    private final CreateBookUseCase createBookUseCase; // Puerto de entrada para la creación de libros

    @Inject
    public BookResource(CreateBookUseCase createBookUseCase) {
        this.createBookUseCase = createBookUseCase;
    }

    /**
     * Crea un nuevo libro en el sistema.
     *
     * @param bookRequest DTO con la información del libro a crear
     * @return Respuesta HTTP con el libro creado
     */
    @POST
    @Operation(summary = "Crear un nuevo libro", description = "Crea un nuevo libro en el sistema")
    @APIResponse(
            responseCode = "201",
            description = "Libro creado exitosamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = BookResponse.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos"
    )
    public Uni<Response> createBook(@Valid BookRequest bookRequest) {
        return createBookUseCase.create(bookRequest)
                .map(bookResponse -> Response.status(Response.Status.CREATED)
                        .entity(bookResponse)
                        .build());
    }
}
