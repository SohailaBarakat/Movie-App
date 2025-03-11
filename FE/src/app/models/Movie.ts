export class Movie {
    id!: number;
    imdbID!: string;
    title!: string;  // ✅ Changed from `Title`
    year!: string;   // ✅ Changed from `Year`
    genre!: string;
    director!: string;
    writer!: string;
    actors!: string;
    plot!: string;
    language!: string;
    poster!: string;  // ✅ Changed from `Poster`
    averageRating!: number;
    userRating!: number;
}
