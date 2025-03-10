package com.movieapp.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieShortInfoDto {
    private Long id;
    private String imdbID;
    private String title;
    private String year;
    private String type;
    private String poster;

}
