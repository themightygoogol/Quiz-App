package ui;

import model.Question;
import model.Quiz;
import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window allowing for editing components of an existing question
 */
public class EditQuestion extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private final Quiz quiz;
    private final Question question;
    private final int index;
    private final String origin;

    private static JFrame frame;
    private static JPanel backgroundPanel;

    private JButton goBack;
    private JLabel editQuestion;
    private JLabel newQuestion;
    private JTextField daQuestion;
    private JLabel newAnswer;
    private JTextField daAnswer;
    private JButton update;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Opens new edit question window
    @SuppressWarnings("methodlength")
    public EditQuestion(QuizSystem quizSystem, User user, Quiz quiz, Question question, int index, String origin) {
        this.quizSystem = quizSystem;
        this.user = user;
        this.quiz = quiz;
        this.question = question;
        this.index = index;
        this.origin = origin;

        frame = new JFrame("Edit Question Menu");
        goBack = new JButton("back");
        editQuestion = new JLabel("Editing question " + index);
        newQuestion = new JLabel("New question:");
        daQuestion = new JTextField(question.getQuestion());
        newAnswer = new JLabel("New answer");
        daAnswer = new JTextField(question.getAnswer());
        update = new JButton("update");
        update.addActionListener(this);
        goBack.addActionListener(this);

        backgroundPanel = new JPanel();
        setPreferredSize(new Dimension(400, 400));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(goBack);
        backgroundPanel.add(editQuestion);
        backgroundPanel.add(newQuestion);
        backgroundPanel.add(daQuestion);
        backgroundPanel.add(newAnswer);
        backgroundPanel.add(daAnswer);
        backgroundPanel.add(update);

        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            question.changeQuestion(daQuestion.getText());
            question.changeAnswer(daAnswer.getText());
            new WhatToDoWithQuestion(quizSystem, user, quiz, question, index, origin);
            frame.dispose();

        } else if (e.getSource() == goBack) {
            new WhatToDoWithQuestion(quizSystem, user, quiz, question, index, origin);
            frame.dispose();
        }
    }
}
