package com.movieapp.service;

import com.movieapp.dto.OmdbSearchResponse;

public interface IOmdbMovieService {
    OmdbSearchResponse search(String title,int page);


}
