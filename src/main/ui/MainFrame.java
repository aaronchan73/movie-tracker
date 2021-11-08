package ui;

import model.Movie;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionListener {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private static final String JSON_FILE = "./data/tracker.json";
    private static final Integer[] RATINGS = {1, 2, 3, 4, 5};
    private Tracker movieList;
    private JsonReader reader;
    private JsonWriter writer;

    JMenuBar menuBar;
    JMenu loadMenu;
    JMenu saveMenu;
    JMenu exitMenu;
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    JTextField newMovieTextField;
    JButton submitButton;
    JComboBox<Integer> ratingBox;

    JButton deleteButton;
    JLabel deleteLabel;
    JPanel deleteMoviePanel;

    JPanel newMoviePanel;

    JPanel buttonPanel;

    JList<String> movieJList;
    DefaultListModel<String> movieJListModel;

    public MainFrame() {

        init();

        GridLayout layout = new GridLayout();
        layout.setRows(2);
        layout.setColumns(1);
        this.setTitle("Movie Tracker");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(layout);
        this.setSize(WIDTH, HEIGHT);

        initMenu();
        initNewMovie();
        initDeleteMovie();

        movieJListModel = new DefaultListModel();
        movieJList = new JList(movieJListModel);

        buttonPanel = new JPanel();
        buttonPanel.add(newMoviePanel);
        buttonPanel.add(deleteMoviePanel);

        this.add(movieJList);
        this.add(buttonPanel);

        this.setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: initializes scanner and movieList
    private void init() {
        movieList = new Tracker();
        reader = new JsonReader(JSON_FILE);
        writer = new JsonWriter(JSON_FILE);
        ImageIcon logo = new ImageIcon("./data/logo.png");
        this.setIconImage(logo.getImage());
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu bar and menu items
    private void initMenu() {
        menuBar = new JMenuBar();
        loadMenu = new JMenu("Load");
        loadItem = new JMenuItem("Load Tracker");
        saveMenu = new JMenu("Save");
        saveItem = new JMenuItem("Save Tracker");
        exitMenu = new JMenu("Exit");
        exitItem = new JMenuItem("Exit Tracker");
        menuBar.add(loadMenu);
        menuBar.add(saveMenu);
        menuBar.add(exitMenu);
        this.setJMenuBar(menuBar);

        loadMenu.add(loadItem);
        saveMenu.add(saveItem);
        exitMenu.add(exitItem);

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: initializes the text field, rating box, and submit button used for new movies
    private void initNewMovie() {
        submitButton = new JButton("Submit");
        newMovieTextField = new JTextField();
        newMovieTextField.setPreferredSize(new Dimension(200, 50));
        newMovieTextField.setText("Movie Title");
        submitButton.addActionListener(this);

        ratingBox = new JComboBox(RATINGS);

        newMoviePanel = new JPanel();
        newMoviePanel.add(newMovieTextField);
        newMoviePanel.add(ratingBox);
        newMoviePanel.add(submitButton);

    }

    // MODIFIES: this
    // EFFECTS: initializes delete movie label and button
    private void initDeleteMovie() {
        deleteMoviePanel = new JPanel();
        deleteButton = new JButton("Delete");
        deleteLabel = new JLabel("Delete Movie");
        deleteMoviePanel.add(deleteLabel);
        deleteMoviePanel.add(deleteButton);

        deleteButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: loads tracker from file
    private void loadRankings() {
        try {
            movieList = reader.read();
            System.out.println(JSON_FILE + " was loaded successfully.");
            ArrayList<String> movieStrings = movieList.displayMovies();

            for (String next : movieStrings) {
                movieJListModel.addElement(next);
            }

        } catch (IOException io) {
            System.out.println(JSON_FILE + " cannot be loaded.");
        }
    }

    // EFFECTS: saves tracker to file
    private void saveRankings() {
        try {
            writer.open();
            writer.write(movieList);
            writer.close();
            System.out.println(JSON_FILE + " was saved successfully.");
        } catch (FileNotFoundException e) {
            System.out.println(JSON_FILE + " cannot be saved.");
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: distributes correct functionality based on inputted action
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadItem) {
            loadRankings();
        } else if (e.getSource() == saveItem) {
            saveRankings();
        } else if (e.getSource() == exitItem) {
            System.exit(0);
        } else if (e.getSource() == submitButton) {
            String movieTitle = newMovieTextField.getText();
            newMovieTextField.setText("");

            int movieRating = ratingBox.getSelectedIndex() + 1;

            Movie movie = new Movie(movieTitle, movieRating);
            movieList.addMovie(movie);

            movieJListModel.addElement(toMovieString(movie));
        } else if (e.getSource() == deleteButton) {
            movieJListModel.removeElement(movieJList.getSelectedValue());
        }
    }

    // EFFECTS: returns a string displaying the name and rating of a movie
    private String toMovieString(Movie m) {
        String name = m.getName();
        String rating = Integer.toString(m.getRating());
        int ranking = movieList.length();

        return ranking + ". " + name + " - " + rating;
    }

}
