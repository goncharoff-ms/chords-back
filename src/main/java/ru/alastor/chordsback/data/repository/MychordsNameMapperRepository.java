package ru.alastor.chordsback.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alastor.chordsback.data.entity.MychordsNameMapper;

@Repository
public interface MychordsNameMapperRepository extends JpaRepository<MychordsNameMapper, Long> {
    Page<MychordsNameMapper> searchByRealNameContainingIgnoreCase(String realName, Pageable pageable);
    boolean existsBySiteName(String siteName);
}