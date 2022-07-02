package ru.alastor.chordsback.data.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import ru.alastor.chordsback.data.entity.Task;
import ru.alastor.chordsback.loader.parsing.ParsingSiteType;
import ru.alastor.chordsback.loader.parsing.TaskType;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Task> findFirstByTypeAndSiteAndCompleted(TaskType type,
                                                      ParsingSiteType site,
                                                      boolean completed);
}