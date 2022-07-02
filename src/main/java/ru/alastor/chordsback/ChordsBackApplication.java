package ru.alastor.chordsback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.alastor.chordsback.data.entity.Task;
import ru.alastor.chordsback.loader.MychordsTaskService;
import ru.alastor.chordsback.loader.parsing.TaskType;

@SpringBootApplication
@EnableScheduling
public class ChordsBackApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ChordsBackApplication.class, args);
    }

    @Autowired
    private MychordsTaskService mychordsService;

    @Override
    public void run(String... args) throws Exception {

//        mychordsService
//                .resolveSongTask(Task.builder()
//                        .type(TaskType.SONG)
//                        .link("/ru/maks_korj/72253-maks-korzh-feat-foth-flyet.html")
//                        .build());

    }
}
