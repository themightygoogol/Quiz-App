package ui;

import model.Event;
import model.EventLog;
import model.QuizSystem;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The start menu for the quiz system
 */
public class StartMenu extends JFrame implements ActionListener, WindowListener {
    private static final String JSON_STORE = "./data/quizSystem.json";
    private QuizSystem quizSystem;
    private User user;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private static JFrame frame;
    private static JPanel backgroundPanel;
    private static JPanel userPanel;

    private JLabel title;
    private JLabel users;
    private JTextField username;
    private JButton go;
    private JButton createNewUser;
    private JButton loadSystem;
    private JButton saveSystem;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: creates new start menu window
    public StartMenu() {

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        quizSystem = new QuizSystem(new ArrayList<>());

        setBackgroundPanel();
        setButtons();

        go.addActionListener(this);
        createNewUser.addActionListener(this);
        loadSystem.addActionListener(this);
        saveSystem.addActionListener(this);

        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);

        frame.addWindowListener(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // EFFECTS: creates a new start menu window with inputted quizSystem
    public StartMenu(QuizSystem quizSystem) {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.quizSystem = quizSystem;

        setBackgroundPanel();
        setButtons();

        go.addActionListener(this);
        createNewUser.addActionListener(this);
        loadSystem.addActionListener(this);
        saveSystem.addActionListener(this);

        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);

        frame.addWindowListener(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    // EFFECTS: adds background components to the start menu window
    private void setBackgroundPanel() {
        frame = new JFrame("Quiz App");
        title = new JLabel("Quiz App");
        users = new JLabel("Users:");

        backgroundPanel = new JPanel();
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(title);
        backgroundPanel.add(users);
    }

    // EFFECTS: adds background buttons to the start menu window
    private void setButtons() {
        username = new JTextField("public");
        go = new JButton("go!");
        createNewUser = new JButton("create new user");
        loadSystem = new JButton("load system");
        saveSystem = new JButton("save system");

        addUsersPanel();
        backgroundPanel.add(userPanel);
        backgroundPanel.add(username);
        backgroundPanel.add(go);
        backgroundPanel.add(createNewUser);
        backgroundPanel.add(loadSystem);
        backgroundPanel.add(saveSystem);

    }

    // EFFECTS: adds users to the start menu window
    private void addUsersPanel() {
        userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(50, 100));

        for (int i = 0; i < quizSystem.getNumberOfUsers(); i++) {
            JLabel newUser = new JLabel(quizSystem.getUser(i).getUsername());
            userPanel.add(newUser);
        }
    }


    // MODIFIES: this
    // EFFECTS: saves quizSystem to file
    private void saveSystem()  {
        try {
            jsonWriter.open();
            jsonWriter.write(quizSystem);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads quizSystem from file
    public void loadUsers() {
        try {
            quizSystem = jsonReader.read();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: Create new window based on button pressed
    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == go) {
            user = quizSystem.findUser(username.getText());
            if (user.getUsername().equals("public")) {
                new MainMenu(quizSystem, user);
                frame.dispose();
            } else if (user != null) {
                new WhatToDoWithUser(quizSystem, user);
                frame.dispose();
            }
        } else if (e.getSource() == createNewUser) {
            new CreateUserScreen(quizSystem);
            frame.dispose();
        } else if (e.getSource() == loadSystem) {
            frame.dispose();
            loadUsers();
            userPanel.removeAll();
            for (int i = 0; i < quizSystem.getNumberOfUsers(); i++) {
                JLabel newUser = new JLabel(quizSystem.getUser(i).getUsername());
                userPanel.add(newUser);
            }
            frame.setVisible(true);
        } else if (e.getSource() == saveSystem) {
            saveSystem();
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    // EFFECTS: Prints all events that have occurred
    @Override
    public void windowClosing(WindowEvent e) {
        EventLog el = EventLog.getInstance();
        for (Event anEvent : el) {
            System.out.println(anEvent);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
