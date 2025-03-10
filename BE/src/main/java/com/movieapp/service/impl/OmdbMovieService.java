package com.movieapp.service.impl;


import com.movieapp.client.OmdbClient;
import com.movieapp.dto.OmdbSearchDto;
import com.movieapp.service.IOmdbMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OmdbMovieService implements IOmdbMovieService {

    @Autowired
    private OmdbClient omdbClient;

    @Value("${omdb.api.key}")
    private String apiKey;

    @Value("${omdb.api.url}")
    private String url;


    @Override
    public OmdbSearchDto search(String name, int page) {
        return omdbClient.searchMovies(name, apiKey,page);
    }

}