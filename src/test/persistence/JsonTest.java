package persistence;

import model.Movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkMovie(String name, int rating, Movie movie){
        assertEquals(name, movie.getName());
        assertEquals(rating, movie.getRating());
    }
}
