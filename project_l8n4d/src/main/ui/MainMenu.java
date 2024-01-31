package ui;

import model.Quiz;
import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main menu for a user to run operations in the quiz app
 */
public class MainMenu extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private Quiz quiz;

    private static JFrame frame;
    private static JPanel quizPanel;
    private static JPanel backgroundPanel;
    private static JScrollPane backgroundScrPane;

    private JButton logout;
    private JLabel hi;
    private JLabel quizzes;
    private SpecialButton anExistingQuiz;
    private JButton newQuiz;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Creates new main menu window
    public MainMenu(QuizSystem quizSystem, User user) {
        this.quizSystem = quizSystem;
        this.user = user;

        setBackgroundPanel();

        frame = new JFrame("Main Menu");
        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // EFFECTS: sets background JFrame components on the main menu window
    private void setBackgroundPanel() {
        logout = new JButton("logout");
        logout.addActionListener(this);
        hi = new JLabel("Welcome " + user.getUsername() + "!");
        quizzes = new JLabel("Quizzes:");
        newQuiz = new JButton("Create New Quiz");
        newQuiz.addActionListener(this);


        backgroundPanel = new JPanel();
        backgroundScrPane = new JScrollPane(backgroundPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setPreferredSize(new Dimension(400, 400));
        add(backgroundScrPane, BorderLayout.CENTER);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(logout);
        backgroundPanel.add(hi);
        backgroundPanel.add(quizzes);
        setQuizzes();
        backgroundPanel.add(newQuiz);
    }

    // EFFECTS: Adds quizzes to the main menu window
    private void setQuizzes() {
        quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(50, 100));

        for (int i = 0; i < user.getNumberOfQuizzes(); i++) {
            anExistingQuiz = new SpecialButton(user.getQuiz(i).getName());
            anExistingQuiz.addActionListener(this);
            anExistingQuiz.setName(user.getQuiz(i).getName());
            quizPanel.add(anExistingQuiz);
        }
        backgroundPanel.add(quizPanel);
    }

    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof SpecialButton) {
            quiz = user.getQuiz(((SpecialButton) e.getSource()).getName());
            new WhatToDoWithQuiz(quizSystem, user, quiz);
            frame.dispose();
        } else if (e.getSource() == logout) {
            new StartMenu(quizSystem);
            frame.dispose();
        } else if (e.getSource() == newQuiz) {
            quiz = new Quiz("");
            new CreateQuiz(quizSystem, user, quiz);
            frame.dispose();
        }
    }
}
