package com.movieapp.service;

import com.movieapp.dto.OmdbSearchDto;

public interface IOmdbMovieService {
    OmdbSearchDto search(String title, int page);


}
