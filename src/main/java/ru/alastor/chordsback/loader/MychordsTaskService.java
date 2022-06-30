package ru.alastor.chordsback.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alastor.chordsback.data.entity.Task;
import ru.alastor.chordsback.data.repository.TaskRepository;
import ru.alastor.chordsback.loader.dto.SongMychords;
import ru.alastor.chordsback.loader.parsing.ParsingSiteType;
import ru.alastor.chordsback.loader.parsing.TaskType;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MychordsTaskService {

    private final MychordsService mychordsService;
    private final TaskRepository taskRepository;


    @Transactional
    public void generateSongTaskOfAuthor(Task task) {
        if (!task.getType().equals(TaskType.SONGS_BY_AUTHOR)) {
            throw new RuntimeException("Неверный тип таски");
        }

        final String authorNameMychords = task.getLink();

        try {
            final List<Task> songTask = mychordsService.getAllSongsByAuthor(authorNameMychords)
                    .stream()
                    .map(s -> Task.builder()
                            .link(s.getLink())
                            .site(ParsingSiteType.MYCHORDS)
                            .type(TaskType.SONG)
                            .createAt(LocalDateTime.now())
                            .build())
                    .toList();

            taskRepository.saveAll(songTask);
        } catch (IOException e) {
            log.error("Ошибка при загрузке песен по имени автора {}", authorNameMychords);
        }

    }

    @Transactional
    public void resolveSongTask(Task task) {
        if (!task.getType().equals(TaskType.SONG)) {
            throw new RuntimeException("Неверный тип таски");
        }

        try {
            SongMychords songByLink = mychordsService.getSongByLink(task.getLink());
        } catch (IOException e) {
            log.error("Ошибка при загрузке песни по ссылке: {}", task.getLink());
        }

    }

}
