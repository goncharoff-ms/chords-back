package ru.alastor.chordsback.api;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.alastor.chordsback.api.dto.SongFindDto;
import ru.alastor.chordsback.api.dto.SongPreviewDto;
import ru.alastor.chordsback.data.entity.Author;
import ru.alastor.chordsback.data.repository.AuthorRepository;
import ru.alastor.chordsback.data.repository.SongRepository;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;

    @GetMapping("/")
    public Page<Author> getAuthorsPages(@PageableDefault(sort = "rate", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Page<SongPreviewDto> getAuthorsSongPage(@PageableDefault(sort = "rate", direction = Sort.Direction.DESC, size = 20)
                                                   Pageable pageable,
                                                   @PathVariable("id") Long id) {
        return songRepository.searchByAuthorId(pageable, id)
                .map(SongPreviewDto::fromEntity);
    }

    @GetMapping("/find")
    public Page<SongFindDto> findSongsLikeQuery(@RequestParam(name = "query") String query,
                                                @PageableDefault(sort = "rate", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        return songRepository.searchByNameLikeIgnoreCaseOrAuthorManyNameLikeIgnoreCase(query, query, pageable)
                .map(SongFindDto::fromEntity);
    }


}
