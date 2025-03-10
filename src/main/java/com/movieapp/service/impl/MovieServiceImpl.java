package com.movieapp.service.impl;

import com.movieapp.client.OmdbClient;
import com.movieapp.dto.MovieDto;
import com.movieapp.dto.MovieShortInfoDto;
import com.movieapp.entity.Movie;
import com.movieapp.exception.handling.BaseException;
import com.movieapp.exception.handling.enums.ErrorMessages;
import com.movieapp.mapper.MovieMapper;
import com.movieapp.repository.MovieRepository;
import com.movieapp.service.IMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class MovieServiceImpl implements IMovieService {

    private final OmdbClient omdbClient;
    private final MovieRepository movieRepository;


    @Value("${omdb.api.key}")
    private String apiKey;

    public MovieServiceImpl(OmdbClient omdbClient, MovieRepository movieRepository) {
        this.omdbClient = omdbClient;
        this.movieRepository = movieRepository;
    }

    @Override
    public void addMovie(String imdbId) {
        if (movieRepository.existsByImdbId(imdbId)) {
            throw new BaseException(ErrorMessages.MOVIE_ALREADY_EXISTS);
        }


        MovieDto movieDto = omdbClient.getMovieDetails(imdbId, apiKey);
        if (movieDto.getResponse().equals("False")) {
            throw new BaseException(ErrorMessages.MOVIE_NOT_FOUND);
        }

        Movie movieEntity = MovieMapper.toEntity(movieDto);

        movieRepository.save(movieEntity);
        log.info("Movie with imdbID {} successfully added to the database.", imdbId);

    }


    @Override
    public void removeMovie(Long movieId) {
        Movie movieEntity = movieRepository.findById(movieId)
                .orElseThrow(() -> new BaseException(ErrorMessages.MOVIE_NOT_FOUND));

        movieRepository.delete(movieEntity);
        log.info("Movie with ID {} successfully removed from the database.", movieId);
    }


    @Override
    public Map<String, Object> getAllMovies(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<MovieShortInfoDto> moviesPage = movieRepository.findAll(pageable)
                .map(MovieMapper::toShortInfoDto);

        Map<String, Object> response = new HashMap<>();
        response.put("totalMovies", moviesPage.getTotalElements());
        response.put("totalPages", moviesPage.getTotalPages());
        response.put("currentPage", moviesPage.getNumber());
        response.put("content", moviesPage.getContent());

        return response;
    }





    @Override
    public MovieDto getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorMessages.MOVIE_NOT_FOUND));

        return MovieMapper.toDto(movie);
    }



    @Override
    public List<MovieShortInfoDto> searchMovies(String title, String year, String director) {
        List<Movie> movies = movieRepository.searchMovies(title, year, director);

        return movies.stream()
                .map(MovieMapper::toShortInfoDto)
                .collect(Collectors.toList());
    }




}
