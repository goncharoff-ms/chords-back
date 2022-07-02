package ru.alastor.chordsback.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.alastor.chordsback.data.entity.Author;
import ru.alastor.chordsback.data.entity.Song;
import ru.alastor.chordsback.data.entity.Task;
import ru.alastor.chordsback.data.repository.AuthorRepository;
import ru.alastor.chordsback.data.repository.SongRepository;
import ru.alastor.chordsback.data.repository.TaskRepository;
import ru.alastor.chordsback.loader.dto.SongMychords;
import ru.alastor.chordsback.loader.parsing.ParsingSiteType;
import ru.alastor.chordsback.loader.parsing.TaskType;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MychordsTaskService {

    private final MychordsService mychordsService;
    private final TaskRepository taskRepository;
    private final SongRepository songRepository;
    private final AuthorRepository authorRepository;

    public Optional<Task> findRandomNotCompletedTaskByType(TaskType type) {
        return taskRepository.findFirstByTypeAndSiteAndCompleted(type, ParsingSiteType.MYCHORDS, false);
    }

    @Transactional
    public void resolveAuthorSongsTask() {
        final Optional<Task> taskOpt = findRandomNotCompletedTaskByType(TaskType.SONGS_BY_AUTHOR);

        taskOpt.ifPresentOrElse(t -> {
            log.info("Нашлась задача поиска песен по автору (taskOpt={})", t);
            final String authorNameMychords = t.getLink();

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

                t.setCompleted(true);
                t.setCompletedAt(LocalDateTime.now());
                taskRepository.save(t);

                log.info("Загружены песни по таске: {}", t);
            } catch (Exception e) {
                log.error("Ошибка при загрузке песен по имени автора {}", authorNameMychords);
                log.error(e.getMessage());
            }
        }, () -> log.info("Нету задач поиска песен по автору"));
    }

    @Transactional
    public void resolveSongTask() {
        final Optional<Task> taskOpt = findRandomNotCompletedTaskByType(TaskType.SONG);

        taskOpt.ifPresentOrElse(t -> {
            log.info("Нашлась задача загрузки песни (taskOpt={})", t);
            try {
                final SongMychords songByLink = mychordsService.getSongByLink(t.getLink());
                final String authorName = songByLink.getAuthor();

                final Author author = authorRepository.findByName(authorName)
                        .orElseGet(() -> authorRepository.save(Author.builder()
                                .name(authorName)
                                .build()));

                songRepository.save(Song.builder()
                        .authorManyName(songByLink.getAuthorFull())
                        .text(songByLink.getText())
                        .rate(songByLink.getRating())
                        .name(songByLink.getName())
                        .author(author)
                        .createAt(LocalDateTime.now())
                        .build());

                t.setCompleted(true);
                t.setCompletedAt(LocalDateTime.now());
                taskRepository.save(t);

                log.info("Загружена песня по таске: {}", t);
            } catch (Exception e) {
                log.error("Ошибка при загрузке песни по ссылке: {}", t.getLink());
                log.error(e.getMessage());
            }
        }, () -> log.info("Нету задач по загрузке песен"));
    }

    @Transactional
    public void resolveMappingMychordsNames() {
        final Optional<Task> taskOpt = findRandomNotCompletedTaskByType(TaskType.UPDATE_MAPPING_MYCHORD_NAMES);

        taskOpt.ifPresentOrElse(t -> {


                },
                () -> log.info("Нету задач по обновлению маппингу имен"));
    }
}
