package ru.alastor.chordsback.api;


import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alastor.chordsback.api.dto.*;
import ru.alastor.chordsback.data.entity.Author;
import ru.alastor.chordsback.data.repository.AuthorRepository;
import ru.alastor.chordsback.data.repository.SongRepository;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Validated
public class AuthorController {
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;

    @GetMapping("/")
    public Page<Author> getAuthorsPages(@PageableDefault(sort = "rate", direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public AuthorSongsDto getAuthorsSongPage(@PageableDefault(sort = "rate", direction = Sort.Direction.DESC, size = 20)
                                             Pageable pageable,
                                             @PathVariable("id") Long id) {
        final Page<SongPreviewDto> song = songRepository.searchByAuthorId(pageable, id)
                .map(SongPreviewDto::fromEntity);

        final AuthorDto author = authorRepository.findById(id).map(AuthorDto::from)
                .orElseThrow();

        return AuthorSongsDto.builder()
                .author(author)
                .songs(song)
                .build();
    }

    private final static Pageable findPage = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "rate"));

    @GetMapping("/find")
    public FindSongAndAuthorDto findSongsLikeQuery(@RequestParam(name = "query") @Length(min = 3) String query) {
        final List<SongFindDto> songs = songRepository.searchAllByNameContainingIgnoreCaseOrAuthorManyNameContainingIgnoreCase(query, query, findPage)
                .stream()
                .map(SongFindDto::fromEntity)
                .toList();
        final List<AuthorDto> authors = authorRepository.searchByNameContainingIgnoreCase(query, findPage)
                .stream()
                .map(AuthorDto::from)
                .toList();

        return FindSongAndAuthorDto.builder()
                .authors(authors)
                .songs(songs)
                .build();
    }


}
