package ru.alastor.chordsback.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alastor.chordsback.data.entity.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Page<Song> searchByAuthorId(Pageable pageable, Long authorId);

    Page<Song> searchAllByNameContainingIgnoreCaseOrAuthorManyNameContainingIgnoreCase(String name, String authorManyName, Pageable pageable);

}