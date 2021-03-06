# ArcMovies
## Overview
The ArcMovies app displays a list of upcoming movies provided by The Movie Database (TMDb). Upon clicking on a movie item, more details are shown on a new screen.

![Screenshot1](https://raw.githubusercontent.com/LBR2048/arc-movies/develop/screenshots/main.png) ![Screenshot2](https://raw.githubusercontent.com/LBR2048/arc-movies/develop/screenshots/details.png)

## Instructions
To be able to use this app you must have a TMDb API key and insert it in your `local.properties` file. The key must be placed inside double quotes.

    apiKey="<your_api_key>"

## Architecture
The app was created using the MVP (Model View Presenter) architecture, where each layer has it own responsibility. This allows for a more modular code that is easier to understand, modify and test. 
The diagram below shows in a visual way how all the classes are connected. The first draft of the diagram was created before writing any piece of code and was updated to reflect its current state.

![Screenshot3](https://raw.githubusercontent.com/LBR2048/arc-movies/develop/screenshots/architecture.png)

The dotted lines represent code that is not fully implemented yet.

## To do
* Optimize screen layout for tablets
* Add a Movie model for the GUI and use UpcomingMovie and MovieDetails exclusively on the repository
(TMDb uses different movie models depending on the API and they implement the genres list in different ways)
* Add a local data repository
* Add movie search capabilities

## Libraries used
### Retrofit
Retrofit was used to access the TMDB API service. Retrofit is the de facto standard for accessing APIs on Android. It handles connection, authentication and other requirements for accessing APIs while allowing the API to be described as annotated Java interfaces:

    @GET("movie/upcoming")
    Call<UpcomingMovies> getUpcomingMovies(@Query("page") int page);

### Picasso
Picasso was used to show poster and backdrop images on the upcoming movies and on the movie details screens. Picasso and Glide are the most used image download libraries available for Android. They take care of handling the connection, cache and other requirements.
