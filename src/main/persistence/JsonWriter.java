package persistence;

import model.Movie;
import model.Tracker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

// Represents writer that writes and saves edits to tracker as JSON data to file
// Code referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {

    private String destination;
    private PrintWriter writer;

    // EFFECTS: create writer that writes to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer to write at destination
    //          throws FileNotFoundException if destination cannot be found or opened
    public void open() throws FileNotFoundException {
        File writingFile = new File(destination);
        writer = new PrintWriter(writingFile);
    }

    // MODIFIES: this
    // EFFECTS: writes tracker onto file as JSON data
    public void write(Tracker tracker) {
        ArrayList<Movie> movieList = tracker.getMovieList();
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Movie next : movieList) {
            JSONObject jsonMovie = new JSONObject();
            String name = next.getName();
            int rating = next.getRating();

            jsonMovie.put("name", name);
            jsonMovie.put("rating", rating);

            jsonArray.put(jsonMovie);
        }

        object.put("movieList", jsonArray);

        writer.print(object);
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

}
