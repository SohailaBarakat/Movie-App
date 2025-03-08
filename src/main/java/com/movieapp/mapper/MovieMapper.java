package com.movieapp.mapper;

import com.movieapp.dto.MovieDto;
import com.movieapp.dto.MovieShortInfoDto;
import com.movieapp.dto.OmdbMovieDto;
import com.movieapp.entity.Movie;
import com.movieapp.entity.User;

public class MovieMapper {

    private MovieMapper() {
        // Private constructor to prevent instantiation
    }



    public static Movie toEntity(MovieDto movieDto) {
        // TODO: pass the Admin id to the method dynamically
        User user = new User();
        user.setId(1L);

        Movie movie = new Movie();
        movie.setImdbId(movieDto.getImdbId());
        movie.setTitle(movieDto.getTitle());
        movie.setYear(movieDto.getYear());
        movie.setGenre(movieDto.getGenre());
        movie.setDirector(movieDto.getDirector());
        movie.setWriter(movieDto.getWriter());
        movie.setActors(movieDto.getActors());
        movie.setPlot(movieDto.getPlot());
        movie.setLanguage(movieDto.getLanguage());
        movie.setPoster(movieDto.getPoster());
        movie.setType(movieDto.getType());
        movie.setAdmin(user);
        return movie;
    }

    public static MovieDto toDto(Movie movie) {
        return MovieDto.builder()
                .imdbId(movie.getImdbId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .writer(movie.getWriter())
                .plot(movie.getPlot())
                .language(movie.getLanguage())
                .actors(movie.getActors())
                .poster(movie.getPoster())
                .type(movie.getType())
                .averageRating(movie.getAverageRating())
                .build();
    }



public static MovieShortInfoDto toShortInfoDto(Movie movie) {
        return MovieShortInfoDto.builder()
                .id(movie.getId())
                .imdbID(movie.getImdbId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .type(movie.getType())
                .poster(movie.getPoster())
                .build();

    }





}
