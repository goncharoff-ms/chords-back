package ru.alastor.chordsback.api.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class AuthorSongsDto {
    private Page<SongPreviewDto> songs;
    private AuthorDto author;
}
