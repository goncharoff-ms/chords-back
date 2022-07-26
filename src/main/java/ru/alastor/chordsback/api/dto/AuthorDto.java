package ru.alastor.chordsback.api.dto;

import lombok.Builder;
import lombok.Data;
import ru.alastor.chordsback.data.entity.Author;

@Data
@Builder
public class AuthorDto {
    private Long id;
    private String name;

    public static AuthorDto from(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

}
