package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TrackerTest {

    private Tracker movieList;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;

    @BeforeEach
    void setup() {
        movieList = new Tracker();

        movie1 = new Movie("Titanic", 5);
        movie2 = new Movie("Naruto", 3);
        movie3 = new Movie("Pacific Rim", 4);

        movieList.addMovie(movie1);
        movieList.addMovie(movie2);
        movieList.addMovie(movie3);
    }

    @Test
    void testAddMovie() {
        assertEquals(3, movieList.length());
    }

    @Test
    void testRemoveMovie() {
        movieList.removeMovie(movie2);

        assertEquals(2, movieList.length());
        assertEquals(movie1, movieList.getMovieAtRanking(1));
        assertEquals(movie3, movieList.getMovieAtRanking(2));
    }

    @Test
    void testGetMovieAtRanking() {
        assertEquals(movie1, movieList.getMovieAtRanking(1));
        assertEquals(movie2, movieList.getMovieAtRanking(2));
        assertEquals(movie3, movieList.getMovieAtRanking(3));
    }

    @Test
    void testMoveMovieUpRankingTopRanking() {
        movieList.moveMovieUpRanking(movie1);
        assertEquals(movie1, movieList.getMovieAtRanking(1));
    }

    @Test
    void testMoveMovieUpRankingMidRanking(){
        movieList.moveMovieUpRanking(movie3);
        assertEquals(movie3, movieList.getMovieAtRanking(2));
        assertEquals(movie2, movieList.getMovieAtRanking(3));

        movieList.moveMovieUpRanking(movie3);
        assertEquals(movie3, movieList.getMovieAtRanking(1));
        assertEquals(movie1, movieList.getMovieAtRanking(2));
    }

    @Test
    void testMoveMovieDownRankingBotRanking() {
        movieList.moveMovieDownRanking(movie3);
        assertEquals(movie3, movieList.getMovieAtRanking(3));
    }

    @Test
    void testMoveMovieDownRankingMidRanking() {
        movieList.moveMovieDownRanking(movie1);
        assertEquals(movie1, movieList.getMovieAtRanking(2));
        assertEquals(movie2, movieList.getMovieAtRanking(1));

        movieList.moveMovieDownRanking(movie1);
        assertEquals(movie1, movieList.getMovieAtRanking(3));
        assertEquals(movie3, movieList.getMovieAtRanking(2));
    }

    @Test
    void testLength() {
        movieList.removeMovie(movie1);
        movieList.removeMovie(movie2);
        movieList.removeMovie(movie3);

        assertEquals(0, movieList.length());

        movieList.addMovie(movie3);

        assertEquals(1, movieList.length());
    }

    @Test
    void testDisplayMovies() {
        movieList.addMovie(new Movie("Matrix", 1));
        ArrayList<String> movieNames = movieList.displayMovies();

        assertEquals("1. Titanic - 5 stars", movieNames.get(0));
        assertEquals("2. Naruto - 3 stars", movieNames.get(1));
        assertEquals("3. Pacific Rim - 4 stars", movieNames.get(2));
        assertEquals("4. Matrix - 1 star", movieNames.get(3));

    }

    @Test
    void testGetMovieList() {
        ArrayList<Movie> testList = new ArrayList<>();
        testList.add(movie1);
        testList.add(movie2);
        testList.add(movie3);
        assertEquals(testList, movieList.getMovieList());
    }
    
    @Test
    void testGetPosition() {
        assertEquals(1, movieList.getPosition(movie1));
        assertEquals(2, movieList.getPosition(movie2));
        assertEquals(3, movieList.getPosition(movie3));

        Movie movie4 = new Movie("Scream", 4);
        movieList.addMovie(movie4);
        assertEquals(4, movieList.getPosition(movie4));
    }

    @Test
    void testGetPositionNoMovie() {
        Movie movie4 = new Movie("Hereditary", 4);
        assertEquals(null, movieList.findMovie(movie4.getName()));
        assertEquals(4, movieList.getPosition(movie4));
    }

    @Test
    void testFindMovie() {
        assertEquals(movie1, movieList.findMovie("Titanic"));
        assertEquals(movie2, movieList.findMovie("Naruto"));
        assertEquals(movie3, movieList.findMovie("Pacific Rim"));
        assertEquals(null, movieList.findMovie("Shrek"));
    }

}