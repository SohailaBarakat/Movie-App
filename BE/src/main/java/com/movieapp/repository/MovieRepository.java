package com.movieapp.repository;

import com.movieapp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByImdbId(String imdbId);


    Optional<Movie> findByImdbId(String imdbId);

    Page<Movie> findAll(Pageable pageable);

    @Query("SELECT m FROM Movie m " +
            "WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:year IS NULL OR m.year = :year) " +
            "AND (:director IS NULL OR LOWER(m.director) LIKE LOWER(CONCAT('%', :director, '%')))")
    List<Movie> searchMovies(@Param("title") String title,
                             @Param("year") String year,
                             @Param("director") String director);

}
