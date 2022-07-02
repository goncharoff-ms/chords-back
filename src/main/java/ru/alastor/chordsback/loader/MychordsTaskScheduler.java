package ru.alastor.chordsback.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.alastor.chordsback.data.repository.TaskRepository;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MychordsTaskScheduler {

    private final MychordsTaskService taskService;

    @Scheduled(fixedRate = 7, timeUnit = TimeUnit.SECONDS)
    public void taskSongAuthorScheduling() {
        taskService.resolveAuthorSongsTask();
    }

    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.SECONDS)
    public void taskSongScheduling() {
        taskService.resolveSongTask();
    }

    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void taskMappingNamesMychords() {
        taskService.resolveMappingMychordsNames();
    }

}
