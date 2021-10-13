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

//    @Test
//    public void testFindRankingOfMovieNoMovie() {
//        Movie movie4 = new Movie("Pitch Perfect", 2);
//        assertEquals(-1, movieList.findRankingOfMovie(movie4));
//    }
//
//    @Test
//    public void testFindRankingOfMovieYesMovie() {
//        assertEquals(2, movieList.findRankingOfMovie(movie2));
//    }

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
        ArrayList<String> movieNames = movieList.displayMovies();

        assertEquals("1. Titanic - 5 stars", movieNames.get(0));
        assertEquals("2. Naruto - 3 stars", movieNames.get(1));
        assertEquals("3. Pacific Rim - 4 stars", movieNames.get(2));

    }

}