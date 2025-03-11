import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { MovieDetailsComponent } from '../movie-details/movie-details.component';
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {Movie} from "../models/Movie";

@Component({
  selector: 'app-film-search',
  templateUrl: './film-search.component.html',
  standalone: true,
  styleUrls: ['./film-search.component.css'],
  imports: [CommonModule, FormsModule]
})
export class FilmSearchComponent implements OnInit {
  movies: Movie[] = []; // ✅ Movies list
  currentPage: number = 1;
  moviesPerPage: number = 8;
  totalPages: number = 1; // ✅ Track total pages
  searchTitle: string = '';
  searchYear: string = '';
  searchDirector: string = '';
  isSearching: boolean = false; // ✅ Track if search is active
  private backendApiUrl = 'http://localhost:8080/api/movie';

  constructor(
      private http: HttpClient,
      public dialog: MatDialog,
      private router: Router
  ) {}

  ngOnInit() {
    console.log("Fetching movies on init...");
    this.fetchMovies(1); // ✅ Load initial movies
  }

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  fetchMovies(page: number = 1) {
    if (this.isSearching) return; // ✅ Don't fetch new pages if searching

    this.currentPage = page;
    const url = `${this.backendApiUrl}?page=${page}&size=${this.moviesPerPage}`; // ✅ Backend is 0-based index

    this.http.get<{ response: any, status: boolean }>(url, { headers: this.getHeaders() })
        .subscribe(
            (data) => {
              console.log('API Response:', data);
              if (data.status && data.response) {
                this.movies = data.response.content || []; // ✅ Extract movies correctly
                this.totalPages = data.response.totalPages || 1;
              } else {
                this.movies = [];
              }
            },
            (error) => {
              console.error('Error fetching movies:', error);
              this.movies = [];
            }
        );
  }

  searchMovies() {
    this.isSearching = true; // ✅ Mark as searching

    const searchParams = new URLSearchParams();
    if (this.searchTitle) searchParams.append('title', this.searchTitle);
    if (this.searchYear) searchParams.append('year', this.searchYear);
    if (this.searchDirector) searchParams.append('director', this.searchDirector);

    const url = `${this.backendApiUrl}/search?${searchParams.toString()}`;

    this.http.get<{ response: Movie[], status: boolean }>(url, { headers: this.getHeaders() })
        .subscribe(
            (data) => {
              this.movies = data.status ? data.response || [] : [];
              this.isSearching = false;
              this.currentPage = 1; // ✅ Reset page for search
            },
            (error) => {
              console.error('Error searching movies:', error);
              this.movies = [];
              this.isSearching = false;
            }
        );
  }

  openFilmDetails(movie: Movie) {
    console.log("Opening details for IMDb ID:", movie.imdbID);
    const url = `http://localhost:8080/api/movie/${movie.imdbID}`;

    this.http.get<{ response: Movie, status: boolean }>(url, { headers: this.getHeaders() })
        .subscribe(
            (data) => {
              if (data.status && data.response) {
                this.dialog.open(MovieDetailsComponent, {
                  data: data.response,
                  width: '500px'
                });
              } else {
                console.error("Movie details not found");
              }
            },
            (error) => console.error("Error fetching movie details:", error)
        );
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.fetchMovies(this.currentPage + 1);
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.fetchMovies(this.currentPage - 1);
    }
  }

  signOut() {
    localStorage.clear();
    this.router.navigate(['/signin']);
  }

  submitRating(movieId: number, rating: number) {
    if (!rating) {
      alert("Please select a rating before submitting.");
      return;
    }

    const url = `http://localhost:8080/api/rating/add?movieId=${movieId}&rating=${rating}`;
    this.http.post(url, {}, { headers: this.getHeaders() }).subscribe(
        () => alert("Rating submitted successfully!"),
        (error) => console.error("Error submitting rating:", error)
    );
  }
}
