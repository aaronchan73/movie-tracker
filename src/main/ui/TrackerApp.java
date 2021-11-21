package ui;

import model.Movie;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Console movie tracker application (Phase 2)
public class TrackerApp {
    private static final String JSON_FILE = "./data/tracker.json";
    private Scanner scanner;
    private Tracker movieList;
    private JsonReader reader;
    private JsonWriter writer;

    // EFFECTS: runs the tracker application
    public TrackerApp() {

        init();
        boolean keepGoing = true;

        while (keepGoing) {

            menuScreen();
            String scannerInput = scanner.next();

            if (scannerInput.equals("e")) {
                keepGoing = false;
                System.out.println("Thank you for using the Movie Tracker.");
            } else {
                userInput(scannerInput);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner and movieList
    private void init() {

        scanner = new Scanner(System.in);
        movieList = new Tracker();
        scanner.useDelimiter("\n");

        reader = new JsonReader(JSON_FILE);
        writer = new JsonWriter(JSON_FILE);

    }

    // MODIFIES: this
    // EFFECTS: processes user input to the tracker
    private void userInput(String scannerInput) {
        switch (scannerInput) {
            case "r":
                checkRankings();
                break;
            case "m":
                newMovie();
                break;
            case "d":
                deleteMovie();
                break;
            case "u":
                moveMovieUpOrDown();
                break;
            case "l":
                loadRankings();
                break;
            case "s":
                saveRankings();
                break;
            default:
                System.out.println("Invalid entry, please try again.");
        }
    }

    // EFFECTS: displays menu screen
    private void menuScreen() {

        System.out.println("Movie Tracker - Select an Option:");
        System.out.println("\tCheck Current Rankings: Press r");
        System.out.println("\tEnter a New Movie: Press m");
        System.out.println("\tRemove a Movie: Press d");
        System.out.println("\tMove a Movie Up or Down a Ranking: Press u");
        System.out.println("\tLoad a Ranking from File: Press l");
        System.out.println("\tSave the Ranking to File: Press s");
        System.out.println("\tExit this Application: Press e");

    }

    // MODIFIES: this
    // EFFECTS: creates a new movie and adds to movieList
    private void newMovie() {

        System.out.println("Enter the movie's name: ");
        String movieName = scanner.next();

        System.out.println("Enter the movie's rating (out of 5 stars): ");
        int movieRating = scanner.nextInt();

        if (movieRating < 0 || movieRating > 5) {
            System.out.println("Inputted rating is not out of 5 stars.");
        } else {
            Movie newMovie = new Movie(movieName, movieRating);
            movieList.addMovie(newMovie);
        }

    }

    // EFFECTS: displays the ranking of movies in the movieList
    private void checkRankings() {

        ArrayList<String> rankingList = movieList.displayMovies();

        for (String next: rankingList) {
            System.out.println(next);
        }

        System.out.println("_________________________________");

    }

    // MODIFIES: this
    // EFFECTS: moves a movie up or down one ranking in movieList
    private void moveMovieUpOrDown() {

        System.out.println("Input the ranking of a movie to move up or down.");
        int movieRank = scanner.nextInt();
        Movie selectedMovie = movieList.getMovieAtRanking(movieRank);

        System.out.println("Move " + selectedMovie.getName() + " up (Press u) or down (Press d) one ranking?");
        String moveSelection = scanner.next();

        if (moveSelection.equals("u")) {
            movieList.moveMovieUpRanking(selectedMovie);
        } else if (moveSelection.equals("d")) {
            movieList.moveMovieDownRanking(selectedMovie);
        } else {
            System.out.println("Invalid entry, please try again.");
        }

    }

    // MODIFIES: this
    // EFFECTS: deletes a movie from movieList
    private void deleteMovie() {
        System.out.println("Input the ranking of the movie you would like to delete.");

        int movieRank = scanner.nextInt();
        Movie removedMovie = movieList.getMovieAtRanking(movieRank);

        movieList.removeMovie(removedMovie);
    }

    // MODIFIES: this
    // EFFECTS: loads tracker from file
    private void loadRankings() {
        try {
            movieList = reader.read();
            System.out.println(JSON_FILE + " was loaded successfully.");

        } catch (IOException io) {
            System.out.println(JSON_FILE + " cannot be loaded.");
        }
    }

    // EFFECTS: saves tracker to file
    private void saveRankings() {
        try {
            writer.open();
            writer.write(movieList);
            writer.close();
            System.out.println(JSON_FILE + " was saved successfully.");
        } catch (FileNotFoundException e) {
            System.out.println(JSON_FILE + " cannot be saved.");
        }
    }

}
