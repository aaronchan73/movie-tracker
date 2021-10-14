package model;

public class Movie {

    private String name;
    private int rating;

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
