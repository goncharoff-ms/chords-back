package ru.alastor.chordsback.loader.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorMychord {
    private String realName;
    private String siteName;
    private Long rate;
}
