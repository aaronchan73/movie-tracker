package ui;

import model.EventLog;
import model.Movie;
import model.Tracker;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// GUI for the Movie Tracker application
public class MainFrame extends JFrame implements ActionListener {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 400;
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

    JLabel addLabel;
    JTextField newMovieTextField;
    JButton addButton;
    JComboBox<Integer> ratingBox;
    JPanel newMoviePanel;

    JButton deleteButton;
    JLabel deleteLabel;
    JPanel deleteMoviePanel;

    JLabel upLabel;
    JButton moveUpButton;
    JLabel downLabel;
    JButton moveDownButton;
    JPanel moveUpDownPanel;

    JPanel buttonPanel;

    JList<String> movieJList;
    DefaultListModel<String> movieJListModel;
    JScrollPane scrollPane;

    JWindow splashWindow;
    ImageIcon splashIcon;

    public MainFrame() {

        splashInit();
        init();
        initMenu();
        initNewMovie();
        initDeleteMovie();
        initMoveMovie();
        initList();

        buttonPanel = new JPanel();
        buttonPanel.add(newMoviePanel);
        buttonPanel.add(deleteMoviePanel);
        buttonPanel.add(moveUpDownPanel);

        this.add(scrollPane);
        this.add(buttonPanel);

        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEvents();
                System.exit(0);
            }
        });

    }

    // MODIFIES: this
    // EFFECTS: initializes JList and DefaultListModel with scroller
    private void initList() {
        movieJListModel = new DefaultListModel();
        movieJList = new JList(movieJListModel);
        scrollPane = new JScrollPane(movieJList);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons and labels for moving movie's position in ranking
    private void initMoveMovie() {
        upLabel = new JLabel("Move Up");
        moveUpButton = new JButton("Up");
        downLabel = new JLabel("Move Down");
        moveDownButton = new JButton("Down");
        moveUpDownPanel = new JPanel();

        moveUpButton.addActionListener(this);
        moveDownButton.addActionListener(this);

        moveUpDownPanel.add(upLabel);
        moveUpDownPanel.add(moveUpButton);
        moveUpDownPanel.add(downLabel);
        moveUpDownPanel.add(moveDownButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes splash window
    private void splashInit() {
        splashIcon = new ImageIcon("./data/splash.png");
        splashWindow = new JWindow();
        splashWindow.getContentPane().add(new JLabel(splashIcon));
        splashWindow.setBounds(0, 0, 500, 400);
        splashWindow.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        splashWindow.setVisible(false);
        splashWindow.dispose();
    }

    // MODIFIES: this
    // EFFECTS: initializes scanner and movieList
    private void init() {
        movieList = new Tracker();
        reader = new JsonReader(JSON_FILE);
        writer = new JsonWriter(JSON_FILE);
        ImageIcon logo = new ImageIcon("./data/logo.png");
        this.setIconImage(logo.getImage());
        GridLayout layout = new GridLayout();
        layout.setRows(2);
        layout.setColumns(1);
        this.setTitle("Movie Tracker");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(layout);
        this.setSize(WIDTH, HEIGHT);
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
        addLabel = new JLabel("Add Movie");
        addButton = new JButton("Submit");
        newMovieTextField = new JTextField();
        newMovieTextField.setPreferredSize(new Dimension(200, 50));
        addButton.addActionListener(this);

        ratingBox = new JComboBox(RATINGS);

        newMoviePanel = new JPanel();
        newMoviePanel.add(addLabel);
        newMoviePanel.add(newMovieTextField);
        newMoviePanel.add(ratingBox);
        newMoviePanel.add(addButton);

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
            displayJList();
            //movieList.logLoadRankings();
        } catch (IOException io) {
            System.out.println(JSON_FILE + " cannot be loaded.");
        }
    }

    // EFFECTS: adds every element in movie list to movie JList
    private void displayJList() {
        ArrayList<String> movieStrings = movieList.displayMovies();
        movieJListModel.clear();

        for (String next : movieStrings) {
            movieJListModel.addElement(next);
        }
    }

    // EFFECTS: saves tracker to file
    private void saveRankings() {
        try {
            writer.open();
            writer.write(movieList);
            writer.close();
            System.out.println(JSON_FILE + " was saved successfully.");
            //movieList.logSaveRankings();
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
            printEvents();
            System.exit(0);
        } else if (e.getSource() == addButton) {
            addMovieToRankings();
        } else if (e.getSource() == deleteButton) {
            Movie selectedMovie = getSelectedMovie();
            movieList.removeMovie(selectedMovie);
            displayJList();
        } else if (e.getSource() == moveUpButton) {
            Movie selectedMovie = getSelectedMovie();
            movieList.moveMovieUpRanking(selectedMovie);
            displayJList();
        } else if (e.getSource() == moveDownButton) {
            Movie selectedMovie = getSelectedMovie();
            movieList.moveMovieDownRanking(selectedMovie);
            displayJList();
        }
    }

    // EFFECTS: prints all the logged events to console
    private void printEvents() {
        for (model.Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds movie into movieList and displays onto movieJList
    private void addMovieToRankings() {
        String movieTitle = newMovieTextField.getText();
        newMovieTextField.setText("");
        int movieRating = ratingBox.getSelectedIndex() + 1;
        Movie movie = new Movie(movieTitle, movieRating);
        movieList.addMovie(movie);
        displayJList();
    }

    // EFFECTS: finds movie in movieList from the selected value from movieJList
    private Movie getSelectedMovie() {
        String selectedValue = movieJList.getSelectedValue();
        String movieName = getMovieName(selectedValue);
        return movieList.findMovie(movieName);
    }

    // EFFECTS: substrings text to get movie's name
    public String getMovieName(String text) {
        int endIndex = text.indexOf("-");
        return text.substring(3, endIndex - 1);
    }

}
