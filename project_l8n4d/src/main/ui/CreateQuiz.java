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
 * Create quiz window allowing for creating a new quiz
 */
public class CreateQuiz extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private final Quiz quiz;
    private Question question;

    private static JFrame frame;
    private static JPanel questionPanel;
    private static JPanel backgroundPanel;

    private JButton back;
    private JTextField quizName;
    private JLabel questions;
    private SpecialButton newQuestion;
    private JButton addQuestion;
    private JButton create;
    private JToggleButton publicButton;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: opens new create quiz window
    public CreateQuiz(QuizSystem quizSystem, User user, Quiz quiz) {
        this.quizSystem = quizSystem;
        this.user = user;
        this.quiz = quiz;

        setBackgroundPanel();

        frame = new JFrame("New Quiz Menu");
        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // EFFECTS: sets components for the create quiz window
    private void setBackgroundPanel() {
        back = new JButton("back");
        back.addActionListener(this);
        quizName = new JTextField("Untitled Quiz");
        questions = new JLabel("Questions:");
        addQuestion = new JButton("Add new Question");
        addQuestion.addActionListener(this);
        publicButton = new JToggleButton("set public");
        create = new JButton("create");
        create.addActionListener(this);

        backgroundPanel = new JPanel();
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(back);
        backgroundPanel.add(quizName);
        backgroundPanel.add(questions);
        setQuestions();
        backgroundPanel.add(addQuestion);
        backgroundPanel.add(publicButton);
        backgroundPanel.add(create);
    }

    // EFFECTS: adds questions to create quiz window
    private void setQuestions() {
        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(50, 100));

        for (int i = 0; i < quiz.getNumberOfQuestions(); i++) {
            JLabel questionNumber = new JLabel("Question " + i);
            JLabel question = new JLabel("Question: " + quiz.getQuestion(i).getQuestion());
            JLabel answer = new JLabel("Answer: " + quiz.getQuestion(i).getAnswer());
            newQuestion = new SpecialButton("edit question " + i);
            newQuestion.addActionListener(this);
            newQuestion.setName(String.valueOf(i));
            questionPanel.add(questionNumber);
            questionPanel.add(question);
            questionPanel.add(answer);
            questionPanel.add(newQuestion);
        }
        backgroundPanel.add(questionPanel);
    }

    // MODIFIES: user
    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof SpecialButton) {
            quiz.changeName(quizName.getText());
            question = quiz.getQuestion(Integer.parseInt(((SpecialButton) e.getSource()).getName()));
            new WhatToDoWithQuestion(quizSystem, user, quiz, question,
                    Integer.parseInt(((SpecialButton) e.getSource()).getName()), "create");
            frame.dispose();
        } else if (e.getSource() == back) {
            new MainMenu(quizSystem, user);
            frame.dispose();
        } else if (e.getSource() == addQuestion) {
            new CreateQuestion(quizSystem, user, quiz, "create");
            frame.dispose();
        } else if (e.getSource() == create) {
            quiz.changeName(quizName.getText());
            user.addQuiz(quiz);
            if (publicButton.isSelected()) {
                User publicUser = quizSystem.findUser("public");
                publicUser.addQuiz(quiz);
            }
            new MainMenu(quizSystem, user);
            frame.dispose();
        }
    }
}
