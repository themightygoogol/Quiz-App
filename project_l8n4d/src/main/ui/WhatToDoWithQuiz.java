package ui;

import model.Quiz;
import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * A window offering operations on a selected quiz
 */
public class WhatToDoWithQuiz extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private final Quiz quiz;

    private static JFrame frame;
    private static JPanel panel;

    private final JButton goBack;
    private final JLabel whatToDo;
    private final JButton take;
    private final JButton edit;
    private final JButton delete;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Creates new WhatToDoWithQuiz window
    @SuppressWarnings("methodlength")
    public WhatToDoWithQuiz(QuizSystem quizSystem, User user, Quiz quiz) {
        this.quizSystem = quizSystem;
        this.user = user;
        this.quiz = quiz;

        frame = new JFrame("What to do");
        goBack = new JButton("back");
        whatToDo = new JLabel(quiz.getName() + " selected");
        take = new JButton("take");
        edit = new JButton("edit");
        delete = new JButton("delete");

        take.addActionListener(this);
        edit.addActionListener(this);
        delete.addActionListener(this);
        goBack.addActionListener(this);

        panel = new JPanel();
        setPreferredSize(new Dimension(400, 400));
        panel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(goBack);
        panel.add(whatToDo);
        panel.add(take);
        panel.add(edit);
        panel.add(delete);

        frame.add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // MODIFIES: user
    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == take) {
            new NextQuestion(quizSystem, user, quiz, 0, 0);
            frame.dispose();
        } else if (e.getSource() == edit) {
            new EditQuiz(quizSystem, user, quiz);
            frame.dispose();
        } else if (e.getSource() == delete) {
            user.removeQuiz(quiz.getName());
            new MainMenu(quizSystem, user);
            frame.dispose();
        } else if (e.getSource() == goBack) {
            new MainMenu(quizSystem, user);
            frame.dispose();
        }
    }

}
