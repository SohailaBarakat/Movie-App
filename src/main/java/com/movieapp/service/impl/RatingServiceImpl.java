package com.movieapp.service.impl;

import com.movieapp.entity.Movie;
import com.movieapp.entity.Rating;
import com.movieapp.entity.User;
import com.movieapp.exception.handling.BaseException;
import com.movieapp.exception.handling.enums.ErrorMessages;
import com.movieapp.repository.MovieRepository;
import com.movieapp.repository.RatingRepository;
import com.movieapp.service.IRatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements IRatingService {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void addRating(Long movieId, int ratingValue) {
        User user = new User();
        user.setId(1L);

        if (ratingValue < 1 || ratingValue > 5) {
            throw new IllegalArgumentException("Rating value must be between 1 and 5.");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new BaseException(ErrorMessages.MOVIE_NOT_FOUND));

        Rating rating = new Rating();
        rating.setMovie(movie);
        rating.setUser(user);
        rating.setRating(ratingValue);

        ratingRepository.save(rating);

        Double averageRating = ratingRepository.findAverageRatingByMovieId(movieId);
        if (averageRating != null) {
            movie.setAverageRating(averageRating);
            movieRepository.save(movie);
        }
    }
}