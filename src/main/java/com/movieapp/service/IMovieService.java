package com.movieapp.service;

import com.movieapp.dto.MovieDto;
import com.movieapp.dto.MovieShortInfoDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IMovieService {
    void addMovie(String adminId);
    void removeMovie(Long movieId);
    Map<String, Object> getAllMovies(int page, int size);
    MovieDto getMovieById(Long id);
    List<MovieShortInfoDto> searchMovies(String title, String year, String director);





}
