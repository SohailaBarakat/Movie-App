package com.movieapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MovieShortInfoDto {
    public MovieShortInfoDto(String imdbID, String title, String year, String type, String poster) {
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
    }

    private Long id;
    private String imdbID;
    private String title;
    private String year;
    private String type;
    private String poster;

}
