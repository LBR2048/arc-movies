# ArcMovies
## Overview
The ArcMovies app displays a list of upcoming movies provided by The Movie Database (TMDb). Upon clicking on a movie item, more details are shown on a new screen.

![Screenshot1](https://raw.githubusercontent.com/LBR2048/arc-movies/develop/screenshots/main.png) ![Screenshot2](https://raw.githubusercontent.com/LBR2048/arc-movies/develop/screenshots/details.png)
## Libraries used
### Retrofit
Retrofit was used to access the TMDB API service. Retrofit is the de facto standard for accessing APIs on Android. It handles connection, authentication and other requirements for accessing APIs while allowing the API to be described as annotated Java interfaces:

    @GET("movie/upcoming")
    Call<UpcomingMovies> getUpcomingMovies(@Query("page") int page);

### Picasso
Picasso was used to show poster and backdrop images on the upcoming movies and on the movie details screens. Picasso and Glide are the most used image download libraries available for Android. They take care of handling the connection, cache and other requirements.
