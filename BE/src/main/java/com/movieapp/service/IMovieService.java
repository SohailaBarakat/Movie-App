package com.movieapp.service;

import com.movieapp.dto.MovieDto;
import com.movieapp.dto.MovieShortInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IMovieService {
    void addMovie(String adminId);
    void removeMovie(String imdbId);
    Map<String, Object> getAllMovies(int page, int size);
    MovieDto getMovieById(String imdbId);
    List<MovieShortInfoDto> searchMovies(String title, String year, String director);





}
