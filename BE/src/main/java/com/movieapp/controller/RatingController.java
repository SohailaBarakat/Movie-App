package com.movieapp.controller;

import com.movieapp.dto.BaseResponse;
import com.movieapp.service.IRatingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@Tag(name = "Rating API", description = "Endpoints for Managing Movie Ratings")
public class RatingController {

    private final IRatingService ratingService;

    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<String>> addRating(
            @RequestParam Long movieId,
            @RequestParam int rating) {
        ratingService.addRating(movieId, rating);
        return ResponseEntity.ok(new BaseResponse<>("Rating added successfully for movie ID: " + movieId));
    }
}