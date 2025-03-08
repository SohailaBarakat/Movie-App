package com.movieapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imdb_id", nullable = false, unique = true)
    private String imdbId;

    @Column(nullable = false)
    private String title;

    private String year;
    private String type;
    private String genre;
    private String poster;
    private String director;
    private String writer;

    @Column(length = 1000)
    private String actors;

    @Lob
    private String plot;

    private String language;

    @Column(name = "average_rating", columnDefinition = "DECIMAL(3, 2) DEFAULT 0.0")
    private double averageRating;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
