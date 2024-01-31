package ui;

import model.Quiz;
import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window showing the next question in a quiz
 */
public class NextQuestion extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private final Quiz quiz;
    private final int currentQuestion;
    private final int correctAnswersCounter;

    private static JFrame frame;
    private static JPanel backgroundPanel;

    private final JButton goBack;
    private final JLabel theQuestion;
    private final JTextField answer;
    private final JButton proceed;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // REQUIRES: correctAnswersCounter >= 0
    // EFFECTS: Creates new next question window
    @SuppressWarnings("methodlength")
    public NextQuestion(QuizSystem quizSystem, User user, Quiz quiz, int currentQuestion, int correctAnswersCounter) {
        this.quizSystem = quizSystem;
        this.user = user;
        this.quiz = quiz;
        this.currentQuestion = currentQuestion;
        this.correctAnswersCounter = correctAnswersCounter;

        frame = new JFrame("Test Page");
        goBack = new JButton("back");
        theQuestion = new JLabel(quiz.getQuestion(currentQuestion).getQuestion());
        answer = new JTextField();
        proceed = new JButton("proceed");
        goBack.addActionListener(this);
        proceed.addActionListener(this);

        backgroundPanel = new JPanel();
        setPreferredSize(new Dimension(800, 400));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(goBack);
        backgroundPanel.add(theQuestion);
        backgroundPanel.add(answer);
        backgroundPanel.add(proceed);

        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // EFFECTS: Create new window based on button pressed and status of the inputted answer
    @SuppressWarnings("methodlength")
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == proceed) {
            if (quiz.getNumberOfQuestions() == currentQuestion + 1) {
                if (answer.getText().equals(quiz.getQuestion(currentQuestion).getAnswer())) {
                    new ResultsPage(quizSystem, user, quiz, correctAnswersCounter + 1);
                    frame.dispose();
                    if ((correctAnswersCounter + 1) / quiz.getNumberOfQuestions() >= 0.5) {
                        new SplashPass();
                    } else {
                        new SplashFail();
                    }
                } else {
                    new ResultsPage(quizSystem, user, quiz, correctAnswersCounter);
                    frame.dispose();
                    if (correctAnswersCounter / quiz.getNumberOfQuestions() >= 0.5) {
                        new SplashPass();
                    } else {
                        new SplashFail();
                    }
                }
            } else {
                if (answer.getText().equals(quiz.getQuestion(currentQuestion).getAnswer())) {
                    new NextQuestion(quizSystem, user, quiz,  currentQuestion + 1, correctAnswersCounter + 1);
                    frame.dispose();
                } else {
                    new NextQuestion(quizSystem, user, quiz, currentQuestion + 1, correctAnswersCounter);
                    frame.dispose();
                }
            }
        } else if (e.getSource() == goBack) {
            new WhatToDoWithQuiz(quizSystem, user, quiz);
            frame.dispose();
        }
    }
}
