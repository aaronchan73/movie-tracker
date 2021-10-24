package persistence;

import model.Movie;
import model.Tracker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads a tracker from JSON data in file
// Code referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {

    private String source;

    // EFFECTS: instantiates reader that reads from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads file and turns JSON data into object
    //          throws IOException if error with reading file occurs
    public Tracker read() throws IOException {
        String data = readFile(source);
        JSONObject jsonObject = new JSONObject(data);
        return parseTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    //          throws IOException if error with reading file occurs
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECT: parses jsonObject as a tracker
    private Tracker parseTracker(JSONObject jsonObject) {
        Tracker tracker = new Tracker();
        addMovies(tracker, jsonObject);
        return tracker;
    }

    // MODIFIES: tracker
    // EFFECTS: parses movies from JSON objects and adds onto tracker
    private void addMovies(Tracker tracker, JSONObject object) {
        JSONArray jsonArray = object.getJSONArray("movieList");
        for (Object next : jsonArray) {
            JSONObject nextObject = (JSONObject) next;
            addMovie(tracker, nextObject);
        }
    }

    // MODIFIES: tracker
    // EFFECTS: parses movie from JSON object and adds onto tracker
    private void addMovie(Tracker tracker, JSONObject object) {
        String name = object.getString("name");
        int rating = object.getInt("rating");
        Movie movie = new Movie(name, rating);
        tracker.addMovie(movie);
    }

}

