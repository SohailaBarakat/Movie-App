package com.movieapp.client;//package com.movieapp.client;

import com.movieapp.dto.MovieDto;
import com.movieapp.dto.OmdbSearchDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.openfeign.FeignClient;



@FeignClient(name = "omdbClient", url = "${omdb.api.url}")
public interface OmdbClient {

    @GetMapping("/")
    OmdbSearchDto searchMovies(
            @RequestParam("s") String query,
            @RequestParam("apikey") String apiKey,
            @RequestParam("page") int page
    );

    @GetMapping
    MovieDto getMovieDetails(
            @RequestParam("i") String imdbId,
            @RequestParam("apikey") String apiKey
    );


}
