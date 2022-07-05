package ru.alastor.chordsback.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/songs")
public class SongController {


    @GetMapping("")
    public void getTopAuthors() {

    }





}
