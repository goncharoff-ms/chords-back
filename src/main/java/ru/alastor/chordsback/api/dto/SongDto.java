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
public class SongDto {
    private Long id;
    private String name;
    private Long rate;
    private String text;
    private String authorManyName;
    private String authorName;

    public static SongDto fromEntity(Song song) {
        return SongDto.builder()
                .id(song.getId())
                .authorName(song.getAuthor().getName())
                .authorManyName(song.getAuthorManyName())
                .rate(song.getRate())
                .text(song.getText())
                .build();
    }
}
