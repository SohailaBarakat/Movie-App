package com.movieapp.controller;


import com.movieapp.dto.BaseResponse;
import com.movieapp.dto.MovieDto;
import com.movieapp.dto.MovieShortInfoDto;
import com.movieapp.entity.Movie;
import com.movieapp.service.IMovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movie")
@Tag(name = "Movie API", description = "Endpoints for Managing Movies")
@Slf4j
public class MovieController {

    private final IMovieService movieService;

    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> addMovie(@RequestParam("imdbId") String imdbId) {
        movieService.addMovie(imdbId);
        return ResponseEntity.ok(new BaseResponse<>("Movie with IMDb ID " + imdbId + " has been added successfully."));
    }

    @DeleteMapping("/remove/{imdbId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> removeMovie(@PathVariable String imdbId ) {
        movieService.removeMovie(imdbId);
        return ResponseEntity.ok(new BaseResponse<>("Movie with imdbID " + imdbId + " has been removed successfully."));
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Map<String, Object>>> getAllMovies(
            @RequestParam(defaultValue = "1") int page,  // User-friendly 1-based page number
            @RequestParam(defaultValue = "10") int size) {

        if (page < 1) {
            return ResponseEntity.badRequest()
                    .body(new BaseResponse<>(Map.of("message", "Page number must be 1 or greater"), false));
        }

        // Adjust user input (1-based page number) to Spring Data's 0-based indexing
        int springPageIndex = page - 1;

        // Get the paginated data from the service layer
        Map<String, Object> response = movieService.getAllMovies(springPageIndex, size);

        // Update the returned "currentPage" in the response to the 1-based index
        response.put("currentPage", page);  // Return 1-based page number to the client

        return ResponseEntity.ok(new BaseResponse<>(response));
    }



    @GetMapping("/{imdbId}")
    public ResponseEntity<BaseResponse<MovieDto>> getMovieById(@PathVariable("imdbId") String imdbId) {
        MovieDto movieDto = movieService.getMovieById(imdbId);
        return ResponseEntity.ok(new BaseResponse<>(movieDto));
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<MovieShortInfoDto>>> searchMovies(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "director", required = false) String director) {
        List<MovieShortInfoDto> movies = movieService.searchMovies(title, year, director);
        return ResponseEntity.ok(new BaseResponse<>(movies));
    }



}
