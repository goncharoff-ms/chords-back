package ru.alastor.chordsback.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alastor.chordsback.data.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}