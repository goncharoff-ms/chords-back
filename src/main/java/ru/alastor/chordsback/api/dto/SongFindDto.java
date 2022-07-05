package ru.alastor.chordsback.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.alastor.chordsback.data.entity.Song;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongFindDto {
    private Long id;
    private String name;
    private Long authorId;
    private String authorName;

    public static SongFindDto fromEntity(Song song) {
        return SongFindDto.builder()
                .id(song.getId())
                .name(song.getName())
                .authorName(song.getAuthor().getName())
                .authorId(song.getAuthor().getId())
                .build();
    }
}
