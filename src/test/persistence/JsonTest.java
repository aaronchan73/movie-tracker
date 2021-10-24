package persistence;

import model.Movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMovie(String name, int rating, Movie movie){
        assertEquals(name, movie.getName());
        assertEquals(rating, movie.getRating());
    }
}
