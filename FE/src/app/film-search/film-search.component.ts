import { Component } from '@angular/core';
import { FilmService } from '../film.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MovieDetailsComponent } from '../movie-details/movie-details.component';
import { MovieService } from '../movie.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-film-search',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './film-search.component.html',
  styleUrls: ['./film-search.component.css']
})
export class FilmSearchComponent {
  movies: any[] = [];
  films: any[] = [];
  addedMovies = new Set<string>(); 
  currentPage: number = 1;
  moviesPerPage: number = 18;
  Math = Math;



  
  private backendApiUrl = 'http://localhost:8080/api/movie/search';

  constructor(private http: HttpClient,private filmService: FilmService, public dialog: MatDialog, private movieService: MovieService, private router: Router) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  search(title: string) {
    this.currentPage = 1;
    this.http.get<{ response: any[], status: boolean }>(`${this.backendApiUrl}?title=${title}`, { headers: this.getHeaders() })
      .subscribe(
        (data) => {
          if (data.status && data.response) {
            this.movies = data.response; // ✅ Correctly accessing movies inside `response`
          } else {
            this.movies = []; // ✅ Clear movies if no data
          }
        },
        (error) => {
          console.error('Error fetching movies:', error);
          this.movies = []; // ✅ Clear movies on error
        }
      );
  }
  


  openFilmDetails(film: any) {
    this.dialog.open(MovieDetailsComponent, {
      data: film,
      width: '500px'
    });
  }

  get paginatedFilms() {
    const startIndex = (this.currentPage - 1) * this.moviesPerPage;
    return this.movies.slice(startIndex, startIndex + this.moviesPerPage);
  }

  nextPage() {
    if ((this.currentPage * this.moviesPerPage) < this.movies.length) {
      this.currentPage++;
    }
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  signOut() {
    localStorage.clear(); // ✅ Clear all stored data
    this.router.navigate(['/signin']); // ✅ Redirect to sign-in page
  }

 
}
