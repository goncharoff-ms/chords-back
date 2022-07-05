package ru.alastor.chordsback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.alastor.chordsback.data.entity.Song;
import ru.alastor.chordsback.data.repository.SongRepository;
import ru.alastor.chordsback.loader.MychordsTaskService;

import java.util.List;

@SpringBootApplication
//@EnableScheduling
public class ChordsBackApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ChordsBackApplication.class, args);
    }

    @Autowired
    private MychordsTaskService mychordsService;

    @Autowired
    private SongRepository songRepository;

    @Override
    public void run(String... args) throws Exception {
//
//        List<Song> objects = songRepository.findAll()
//                .stream()
//                .peek(s -> s.setName(s.getName().substring(1)))
//                .peek(s -> s.setAuthorManyName(s.getAuthorManyName().substring(0, s.getAuthorManyName().length() - 1)))
//                .toList();
//
//        songRepository.saveAll(objects);

//        mychordsService
//                .resolveSongTask(Task.builder()
//                        .type(TaskType.SONG)
//                        .link("/ru/maks_korj/72253-maks-korzh-feat-foth-flyet.html")
//                        .build());

    }
}
