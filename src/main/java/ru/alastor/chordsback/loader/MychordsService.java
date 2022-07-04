package ru.alastor.chordsback.loader;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.alastor.chordsback.loader.dto.AuthorMychord;
import ru.alastor.chordsback.loader.dto.SongLight;
import ru.alastor.chordsback.loader.dto.SongMychords;
import ru.alastor.chordsback.loader.parsing.MychordsParser;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MychordsService {

    public static final String BASE_PATH_AUTHOR = "https://music.mychords.net";

    private final MychordsParser parser;

    public List<SongLight> getAllSongsByAuthor(String authorNameMychord) throws IOException, InterruptedException {
        final List<SongLight> songs = new ArrayList<>();
        boolean isLoadCountPages = false;
        int countPages = 1;
        int nowPage = 1;

        do {

            Thread.sleep(1000L);
            final Document doc = Jsoup.connect(BASE_PATH_AUTHOR + "/" + authorNameMychord + "/page/" + nowPage++)
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

    public List<CompletableFuture<List<AuthorMychord>>> getMappingNameRussianAuthorStream() throws IOException {
        final Executor delayed = CompletableFuture.delayedExecutor(3L, TimeUnit.SECONDS);
        final List<CompletableFuture<List<AuthorMychord>>> completableFutures = new ArrayList<>();

        final Document doc = Jsoup.connect(BASE_PATH_AUTHOR + "/ru" + "/nashi/page/1")
                .userAgent("Chrome/4.5.249.0 Safari/542.1")
                .referrer("https://www.ya.ru")
                .get();

        int countPages = parser.getCountPages(doc);

        for (int i = 1; i <= countPages; i++) {
            final int finalI = i;
            completableFutures.add(CompletableFuture.supplyAsync(() -> getAuthorsOnPage(finalI), delayed));
        }

        return completableFutures;
    }


    @SneakyThrows
    private List<AuthorMychord> getAuthorsOnPage(int pageNumber) {
        final Document doc = Jsoup.connect(BASE_PATH_AUTHOR + "/ru/" + "/nashi/page/" + pageNumber)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("https://www.google.com")
                .get();

        return parser.getAuthors(doc);
    }


}
