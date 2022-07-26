package ru.alastor.chordsback.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alastor.chordsback.api.dto.SongDto;
import ru.alastor.chordsback.data.repository.SongRepository;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongRepository songRepository;

    @GetMapping("")
    public void getTopAuthors() {

    }

    @GetMapping("/{id}")
    public SongDto getSongById(@PathVariable("id") Long id) {
        return songRepository.findById(id)
                .map(SongDto::fromEntity)
                .orElseThrow();
    }


}
