package org.jegdev.library.books.infrastructure.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int stock;
    private Instant createdAt;
}
