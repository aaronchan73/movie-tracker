package persistence;

import model.Movie;
import model.Tracker;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriteInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/\nillegal-file.json");
            writer.open();
            fail();
        } catch(FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyFile() {
        try {
            Tracker tracker = new Tracker();
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyFile.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyFile.json");
            tracker = reader.read();
            assertEquals(0, tracker.length());
        } catch(IOException e) {
            fail();
        }
    }

    @Test
    void testWriteFilledFile() {
        try {
            Tracker tracker = new Tracker();
            tracker.addMovie(new Movie("Interstellar", 5));
            tracker.addMovie(new Movie("The Matrix", 4));
            JsonWriter writer = new JsonWriter("./data/testWriteFilledFile.json");
            writer.open();
            writer.write(tracker);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteFilledFIle.json");
            tracker = reader.read();
            assertEquals(2, tracker.length());
            assertEquals("Interstellar", tracker.getMovieAtRanking(1).getName());
            assertEquals("The Matrix", tracker.getMovieAtRanking(2).getName());
            assertEquals(5, tracker.getMovieAtRanking(1).getRating());
            assertEquals(4, tracker.getMovieAtRanking(2).getRating());

        } catch(IOException e) {
            fail();
        }
    }
}
