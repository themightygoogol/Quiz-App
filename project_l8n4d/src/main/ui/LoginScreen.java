package ui;

import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window to log in with a selected user
 */
public class LoginScreen extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;

    private static JFrame frame;
    private static JPanel backgroundPanel;
    private static JScrollPane backgroundScrPane;

    private final JButton goBack;
    private final JLabel loggingInWith;
    private final JTextField password;
    private final JButton login;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Creates new login screen window
    public LoginScreen(QuizSystem quizSystem, User user) {
        this.quizSystem = quizSystem;
        this.user = user;

        frame = new JFrame("Login Menu");
        goBack = new JButton("back");
        loggingInWith = new JLabel("Logging in with " + user.getUsername());
        password = new JTextField("password");
        login = new JButton("login");
        login.addActionListener(this);
        goBack.addActionListener(this);

        backgroundPanel = new JPanel();
        backgroundScrPane = new JScrollPane(backgroundPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setPreferredSize(new Dimension(400, 400));
        add(backgroundScrPane, BorderLayout.CENTER);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(goBack);
        backgroundPanel.add(loggingInWith);
        backgroundPanel.add(password);
        backgroundPanel.add(login);

        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            if (password.getText().equals(user.getPassword())) {
                new MainMenu(quizSystem, user);
                frame.dispose();
            }
        } else if (e.getSource() == goBack) {
            new WhatToDoWithUser(quizSystem, user);
            frame.dispose();
        }
    }
}
