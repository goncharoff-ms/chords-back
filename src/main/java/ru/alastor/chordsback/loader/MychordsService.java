package ru.alastor.chordsback.loader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.alastor.chordsback.loader.dto.SongLight;
import ru.alastor.chordsback.loader.dto.SongMychords;
import ru.alastor.chordsback.loader.parsing.MychordsParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MychordsService {

    public static final String BASE_PATH_AUTHOR = "https://music.mychords.net";

    private final MychordsParser parser;

    public List<SongLight> getAllSongsByAuthor(String authorNameMychord) throws IOException {
        final List<SongLight> songs = new ArrayList<>();
        boolean isLoadCountPages = false;
        int countPages = 1;
        int nowPage = 1;

        do {

            final Document doc = Jsoup.connect("/ru/" + BASE_PATH_AUTHOR + "/" + authorNameMychord + "/page/" + nowPage++)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("https://www.google.com")
                    .get();

            if (!isLoadCountPages) {
                countPages = parser.getCountPages(doc);
                isLoadCountPages = true;
            }

            final List<SongLight> lightSongs = parser.getLightSongs(doc);

            songs.addAll(lightSongs);
        } while (nowPage <= countPages);

        log.info("Get all songs {}, count: {}", authorNameMychord, songs.size());
        return songs;
    }

    public SongMychords getSongByLink(String linkSong) throws IOException {
        final Document doc = Jsoup.connect(BASE_PATH_AUTHOR + linkSong)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("https://www.google.com")
                .get();

        log.info("Load song {}", linkSong);

        return parser.getSong(doc);
    }


}
