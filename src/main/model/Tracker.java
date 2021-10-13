package model;

import java.util.ArrayList;

public class Tracker {

    private final ArrayList<Movie> movieList;

    public Tracker() {
        movieList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds movie at the end of the tracker
    public void addMovie(Movie m) {
        movieList.add(m);
    }

    // MODIFIES: this
    // EFFECTS: removes specified movie from the tracker
    public void removeMovie(Movie m) {
        movieList.remove(m);
    }

    // EFFECTS: produces movie at a specified ranking
    public Movie getMovieAtRanking(int rank) {
        return movieList.get(rank - 1);
    }

//    // EFFECTS: returns the ranking of a given movie, -1 if the movie
//    //          is not found
//    public int findRankingOfMovie(Movie m) {
//        int rank = 0;
//        for (Movie next : movieList) {
//            rank++;
//            if (m == next) {
//                return rank;
//            }
//        }
//        return -1;
//    }

    // REQUIRES: there is more than one movie in the tracker
    // MODIFIES: this
    // EFFECTS: leaves the movie in the same ranking if at the top of the tracker,
    //          moves the specified movie up one ranking in the tracker
    public void moveMovieUpRanking(Movie m) {
        if (movieList.indexOf(m) == 0) {
            movieList.set(0, m);
        } else {
            int currRank = movieList.indexOf(m);
            Movie prevMovie = movieList.get(currRank - 1);
            movieList.set((currRank - 1), m);
            movieList.set(currRank, prevMovie);
        }
    }

    // REQUIRES: there is more than one movie in the tracker
    // MODIFIES: this
    // EFFECTS: leaves the movie in the same ranking if at the end of the tracker,
    //          otherwise moves the specified movie down one ranking in the tracker
    public void moveMovieDownRanking(Movie m) {
        if (movieList.indexOf(m) == (movieList.size() - 1)) {
            movieList.set((movieList.size() - 1), m);
        } else {
            int currRank = movieList.indexOf(m);
            Movie nextMovie = movieList.get(currRank + 1);
            movieList.set((currRank + 1), m);
            movieList.set(currRank, nextMovie);
        }
    }

    // EFFECTS: returns the amount of movies in the tracker
    public int length() {
        return movieList.size();
    }

    // EFFECTS: lists out ranking of movies in movieList
    public ArrayList<String> displayMovies() {

        ArrayList<String> movieRanking = new ArrayList<>();

        int i = 0;

        for (Movie next : movieList) {
            i++;
            String ratingString = Integer.toString(next.getRating());
            String nameString = next.getName();
            String rankingString = Integer.toString(i);
            String movieString = rankingString + ". " + nameString + " - " + ratingString + " stars";
            movieRanking.add(movieString);
        }

        return movieRanking;

    }

}