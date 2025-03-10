package com.movieapp.controller;

import com.movieapp.dto.BaseResponse;
import com.movieapp.dto.OmdbSearchDto;
import com.movieapp.service.IOmdbMovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/omdb")
@Tag(name = "OMDB Movie API", description = "Endpoints for accessing OMDB movie information")
public class OmdbMovieController {

    @Autowired
    private IOmdbMovieService omdbMovieService;



    @RequestMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<OmdbSearchDto>> search(
            @RequestParam(name = "title") String title,
            @RequestParam(defaultValue = "1") int page) {
        OmdbSearchDto response = omdbMovieService.search(title, page);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }

}
