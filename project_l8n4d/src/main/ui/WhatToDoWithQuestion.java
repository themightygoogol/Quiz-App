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
 * A window offering operations on a selected question
 */
public class WhatToDoWithQuestion extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private final Quiz quiz;
    private final Question question;
    private final int index;
    private final String origin;

    private static JFrame frame;
    private static JPanel panel;

    private final JButton goBack;
    private final JLabel whatToDo;
    private final JButton edit;
    private final JButton delete;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Creates new WhatToDoWithQuestion window
    @SuppressWarnings("methodlength")
    public WhatToDoWithQuestion(QuizSystem quizSystem, User user, Quiz quiz, Question question, int index,
                                String origin) {
        this.quizSystem = quizSystem;
        this.user = user;
        this.quiz = quiz;
        this.question = question;
        this.index = index;
        this.origin = origin;

        frame = new JFrame("What to do");
        goBack = new JButton("back");
        whatToDo = new JLabel("Question: " + question.getQuestion() + " selected");
        edit = new JButton("edit");
        delete = new JButton("delete");

        edit.addActionListener(this);
        delete.addActionListener(this);
        goBack.addActionListener(this);

        panel = new JPanel();
        setPreferredSize(new Dimension(600, 400));
        panel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(goBack);
        panel.add(whatToDo);
        panel.add(edit);
        panel.add(delete);

        frame.add(panel);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    // Modifies: quiz
    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == edit) {
            new EditQuestion(quizSystem, user, quiz, question, index, origin);
            frame.dispose();
        } else if (e.getSource() == delete) {
            if (origin.equals("create")) {
                quiz.removeQuestion(index);
                new CreateQuiz(quizSystem, user, quiz);
                frame.dispose();
            } else if (origin.equals("edit")) {
                quiz.removeQuestion(index);
                new EditQuiz(quizSystem, user, quiz);
                frame.dispose();
            }
        } else if (e.getSource() == goBack) {
            if (origin.equals("create")) {
                new CreateQuiz(quizSystem, user, quiz);
                frame.dispose();
            } else if (origin.equals("edit")) {
                new EditQuiz(quizSystem, user, quiz);
                frame.dispose();
            }
        }
    }
}
