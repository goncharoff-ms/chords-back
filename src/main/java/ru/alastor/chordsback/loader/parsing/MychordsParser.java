package ru.alastor.chordsback.loader.parsing;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
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
        final long rate = Long.parseLong(doc.select("#container2 > main " +
                "> div:nth-child(2) > div.pure-g.b-actions " +
                "> div.pure-u.pure-u-md-10-24 > div " +
                "> span.b-viewed > span.b-viewed__count").text());
        final String title = doc.select(".b-title--song").text();

        final String[] authorAndTitle = title.split("-");
        final String authorNameFull = authorAndTitle[0];
        final String songName = authorAndTitle[1];
        final String author = doc.select(".b-breadcrumbs > span:nth-last-child(1) > a > span").text();


        final var textBuilder = new StringBuilder();

        final var textSongElements = doc.select(".w-words__text")
                .get(0)
                .childNodes();

        for (Node songElement : textSongElements) {
            if (songElement instanceof TextNode textNode) {
                textBuilder.append(textNode.getWholeText());
            } else if (songElement instanceof Element textElement) {
                if (!isChord(textElement)) {
                    continue;
                }

                try {
                    final String chord = textElement.select(".b-accord__symbol > a")
                            .attr("data-title")
                            .split("/akkords/")[1]
                            .split(".png")[0];

                    textBuilder.append("{{").append(chord).append("}}");
                } catch (Exception e) {
                    log.error("parse error");
                }
            }
        }

        return SongMychords.builder()
                .name(songName)
                .rating(rate)
                .text(textBuilder.toString())
                .author(author)
                .authorFull(authorNameFull)
                .build();
    }

    private final static List<String> stopWords = List.of(
            "Взято с сайта"
    );

    private boolean isChord(Element element) {
        return stopWords.stream()
                .noneMatch(sw -> element.text().contains(sw));
    }

}
