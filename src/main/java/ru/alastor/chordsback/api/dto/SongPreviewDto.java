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
public class SongPreviewDto {
    private Long id;
    private String name;
    private String authorManyName;

    public static SongPreviewDto fromEntity(Song song) {
        return SongPreviewDto.builder()
                .id(song.getId())
                .name(song.getName())
                .authorManyName(song.getAuthorManyName())
                .build();
    }
}
