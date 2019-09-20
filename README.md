# Popular Movies App

This app displays a list of popular movies pulled from The Movie Database (TMDb), and allows users to change the sorting order by most popular, highest-rated, or favorite movies that they've previously saved. 

When a user taps on a movie poster, they are transitioned to a details screen that shows additional information on the selected movie, to include Release Date, Average Rating, a Mark as Favorite checkbox, and Plot Synopsis. The user can also scroll through tabs to see any available movie trailers or videos, as well as reviews on the selected movie.

More info on The Movie Database API available at: https://www.themoviedb.org/documentation/api


## Getting Started

This app uses the Gradle build system. To build this project, use the "./gradlew build" command or use "Import Project" in Android Studio. Additionally, if you don't already have an account with TMDb, you will need to create one in order to request an API Key (https://www.themoviedb.org/account/signup). Once you have an API Key, you can add it to the **apiKey** variable in the MovieMainRepository.java and MovieDetailRepository.java file.

## Screenshots

![](https://github.com/dcronin202/PopularMovies/blob/master/screenshots/popularViewScreenshot.png)   ![](https://github.com/dcronin202/PopularMovies/blob/master/screenshots/favoritesViewScreenshot.png)  

![](https://github.com/dcronin202/PopularMovies/blob/master/screenshots/detailTabScreenshot.png)  ![](https://github.com/dcronin202/PopularMovies/blob/master/screenshots/reviewTabScreenshot.png)   ![](https://github.com/dcronin202/PopularMovies/blob/master/screenshots/videoTabScreenshot.png)  