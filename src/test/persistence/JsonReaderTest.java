package persistence;

import model.Tracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest {

    @Test
    void testReadNoFile() {
        JsonReader reader = new JsonReader("./data/testReadNoFile.json");
        try {
            reader.read();
            fail();
        } catch (IOException io) {
            // pass
        }
    }

    @Test
    void testReadEmptyFile() {
        JsonReader reader = new JsonReader("./data/testReadEmptyFile.json");
        try {
            Tracker tracker = reader.read();
            int numMovies = tracker.length();
            assertEquals(0, numMovies);
        } catch (IOException io) {
            fail();
        }
    }

    @Test
    void testReadFilledFile() {
        JsonReader reader = new JsonReader("./data/testReadFilledFile.json");
        try {
            Tracker tracker = reader.read();
            assertEquals(3, tracker.length());
            assertEquals("Titanic", tracker.getMovieAtRanking(1).getName());
            assertEquals("Shrek", tracker.getMovieAtRanking(2).getName());
            assertEquals("Hachi", tracker.getMovieAtRanking(3).getName());
            assertEquals(3, tracker.getMovieAtRanking(1).getRating());
            assertEquals(5, tracker.getMovieAtRanking(2).getRating());
            assertEquals(4, tracker.getMovieAtRanking(3).getRating());
        } catch (IOException io) {
            fail();
        }
    }

}
