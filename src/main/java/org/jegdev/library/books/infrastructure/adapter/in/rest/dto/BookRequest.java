package org.jegdev.library.books.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "BookRequest", description = "Datos requeridos para la creación de un nuevo libro")
public class BookRequest {

    // Identificador del libro, se genera automaticamente desde MongoDB

    @Schema(description = "Titulo del libro", example = "El Principito" , required = true)
    @NotBlank(message = "El titulo del libro no puede estar vacio.")
    private String title;

    @Schema(description = "Autor del libro", example = "Antoine de Saint-Exupéry" , required = true)
    @NotBlank(message = "El libro debe tener al menos un autor.")
    private String author;

    @Schema(description = "ISBN del libro", example = "978-3-16-148410-0" , required = true)
    @NotBlank(message = "El isbn del libro es obligatorio.")
    @Size(min = 10, max = 13)
    private String isbn;

    @Schema(description = "Cantidad de libros que hay en stock", example = "5" , required = true)
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;
}
