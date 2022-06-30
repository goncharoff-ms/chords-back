package ru.alastor.chordsback.loader.parsing;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import ru.alastor.chordsback.loader.dto.SongLight;
import ru.alastor.chordsback.loader.dto.SongMychords;

import java.util.List;

@Component
@Slf4j
public class MychordsParser {


    public int getCountPages(Document doc) {
        return doc.select(".b-pagination > li")
                .stream()
                .filter(e -> !e.hasClass("next"))
                .map(e -> Integer.parseInt(e.text()))
                .max(Integer::compareTo)
                .orElseThrow();
    }


    public List<SongLight> getLightSongs(Document doc) {
        return doc.select(".b-listing__item")
                .stream()
                .map(e -> {
                    String link = e.select(".b-listing__item__link").attr("href");
                    return SongLight.builder().link(link).build();
                }).toList();
    }

    public SongMychords getSong(Document doc) {
        final long rate = Long.parseLong(doc.select(".b-viewed__count").text());
        final String title = doc.select(".b-title--song").text();

        final String[] authorAndTitle = title.split("-");
        final String authorNameFull = authorAndTitle[0];
        final String songName = authorAndTitle[1];
        final String author = doc.select(".b-breadcrumbs > span:nth-last-child(1) > a > span").text();


        // TODO: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        final String text = doc.select(".w-words__text").outerHtml();



        return SongMychords.builder()
                .name(songName)
                .rating(rate)
                .text(text)
                .author(author)
                .authorFull(authorNameFull)
                .build();
    }

}
