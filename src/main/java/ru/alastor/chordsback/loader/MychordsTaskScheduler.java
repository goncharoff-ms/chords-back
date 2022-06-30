package ru.alastor.chordsback.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MychordsTaskScheduler {

    private final MychordsTaskService taskService;


    public void generateSongTaskOfAuthor() {

    }

}
