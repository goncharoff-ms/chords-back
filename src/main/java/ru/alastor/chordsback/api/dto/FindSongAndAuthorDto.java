package ru.alastor.chordsback.api.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindSongAndAuthorDto {
    private List<AuthorDto> authors;
    private List<SongFindDto> songs;
}
