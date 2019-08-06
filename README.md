# Popular Movies App - Stage 1

This app displays a list of popular movies pulled from The Movie Database (TMDb), and allows users to change the sorting order by most popular or by highest-rated. When a user taps on a movie poster, they will be transitioned to a details screen that shows additional information on the selected movie, to include Original Title, Release Date, Average Rating, and Plot Synopsis.

More info on The Movie Database API available at: https://www.themoviedb.org/documentation/api


## Pre-requisites

- Android SDK v28
- Android Support Repository 28.0.0


## Getting Started

This sample uses the Gradle build system. To build this project, use the "gradlew build" command or use "Import Project" in Android Studio. Additionally, if you don't already have an account with TMDb, you will need to create one in order to request an API Key (https://www.themoviedb.org/account/signup). Once you have an API Key, you can add it to the **apiKey** in the MainActivityFragment.java file.