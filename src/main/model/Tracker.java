package model;

import java.util.ArrayList;

// Represents a movie tracker as a list of movies
public class Tracker {

    private final ArrayList<Movie> movieList;

    // EFFECTS: instantiates the movieList
    public Tracker() {
        movieList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds movie at the end of the tracker
    public void addMovie(Movie m) {
        movieList.add(m);
        EventLog.getInstance().logEvent(new Event(m.getName() + " was added to tracker."));
    }

    // MODIFIES: this
    // EFFECTS: removes specified movie from the tracker
    public void removeMovie(Movie m) {
        movieList.remove(m);
        EventLog.getInstance().logEvent(new Event(m.getName() + " was removed from tracker."));
    }

    // EFFECTS: produces movie at a specified ranking
    public Movie getMovieAtRanking(int rank) {
        return movieList.get(rank - 1);
    }

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

        EventLog.getInstance().logEvent(new Event(m.getName() + " is moved up one ranking."));
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

        EventLog.getInstance().logEvent(new Event(m.getName() + " is moved down one ranking."));
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
            String movieString;
            if (next.getRating() == 1) {
                movieString = rankingString + ". " + nameString + " - " + ratingString + " star";
            } else {
                movieString = rankingString + ". " + nameString + " - " + ratingString + " stars";
            }
            movieRanking.add(movieString);
        }

        return movieRanking;

    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    // EFFECTS: gets position of inputted movie in movieList
    public int getPosition(Movie movie) {
        int i = 1;
        for (Movie next : movieList) {
            if (movie == next) {
                return i;
            } else {
                i++;
            }
        }
        return i;
    }

    // EFFECTS: retrieves movie in movieList with given name
    public Movie findMovie(String name) {
        for (Movie next : movieList) {
            if (next.getName().equals(name)) {
                return next;
            }
        }

        return null;

    }

//    // EFFECTS: logs the method of saving rankings
//    public void logSaveRankings() {
//        EventLog.getInstance().logEvent(new Event("Tracker has been saved."));
//    }
//
//    // EFFECTS: logs the methods of loading rankings
//    public void logLoadRankings() {
//        EventLog.getInstance().logEvent((new Event("Tracker has been loaded.")));
//    }

}
