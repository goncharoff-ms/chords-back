package ru.alastor.chordsback.api.admin;


import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alastor.chordsback.data.entity.MychordsNameMapper;
import ru.alastor.chordsback.data.entity.Task;
import ru.alastor.chordsback.data.repository.MychordsNameMapperRepository;
import ru.alastor.chordsback.data.repository.TaskRepository;
import ru.alastor.chordsback.loader.parsing.ParsingSiteType;
import ru.alastor.chordsback.loader.parsing.TaskType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    private final MychordsNameMapperRepository mychordsNameMapperRepository;
    private final TaskRepository taskRepository;


    private final String taskHtml;
    private final static Pageable FIND_PAGE = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "rate"));


    {
        File file = new File(getClass().getResource("/static/task.html").getFile());
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        taskHtml = contentBuilder.toString();
    }

    public AdminController(MychordsNameMapperRepository mychordsNameMapperRepository, TaskRepository taskRepository) {
        this.mychordsNameMapperRepository = mychordsNameMapperRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/task")
    private String index() {
        return taskHtml;
    }

    @PostMapping("/task/author")
    public void createAuthorTask(@RequestParam(name = "siteName")  String siteNameAuthor) {
        if (!mychordsNameMapperRepository.existsBySiteName(siteNameAuthor)) {
            throw new RuntimeException("нет такого автора на майчродсе");
        }
        taskRepository.save(Task.builder()
                        .site(ParsingSiteType.MYCHORDS)
                        .completed(false)
                        .link(siteNameAuthor)
                        .createAt(LocalDateTime.now())
                        .type(TaskType.SONGS_BY_AUTHOR)
                .build());
    }

    @GetMapping("/mappingnames/find")
    public List<MychordsNameMapper> findMappingNamesMychord(@RequestParam(name = "query") @Length(min = 3) String query) {
        return mychordsNameMapperRepository.searchByRealNameContainingIgnoreCase(query, FIND_PAGE)
                .getContent();
    }


}
