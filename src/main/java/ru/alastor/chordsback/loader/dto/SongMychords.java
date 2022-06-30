package ru.alastor.chordsback.loader.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongMychords {
    private String author;
    private String authorFull;
    private String name;
    private Long rating;
    private String text;
}
