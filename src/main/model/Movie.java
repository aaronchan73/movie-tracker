package model;

// Represents a movie with a name and rating
public class Movie {

    private String name;
    private int rating;

    // EFFECTS: the name of the movie is set to name,
    //          the rating of the movie is set to rating
    public Movie(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

}
