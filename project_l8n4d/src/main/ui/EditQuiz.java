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
 * A window to edit an existing quiz
 */
public class EditQuiz extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private User publicUser;
    private boolean quizIsPublic;
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
    private JToggleButton publicButton;
    private JButton save;


    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Creates new edit quiz window
    public EditQuiz(QuizSystem quizSystem, User user, Quiz quiz) {
        this.quizSystem = quizSystem;
        this.user = user;
        this.quiz = quiz;
        publicUser = quizSystem.findUser("public");
        if (publicUser.getQuiz(quiz.getName()) == null) {
            quizIsPublic = false;
        } else {
            quizIsPublic = true;
        }

        setBackgroundPanel();

        frame = new JFrame("Edit Quiz Menu");
        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // EFFECTS: Sets the background objects on the edit quiz window
    @SuppressWarnings("methodlength")
    private void setBackgroundPanel() {
        back = new JButton("Back");
        back.addActionListener(this);
        quizName = new JTextField(quiz.getName());
        questions = new JLabel("Questions:");
        addQuestion = new JButton("Add Question");
        addQuestion.addActionListener(this);
        if (quizIsPublic) {
            publicButton = new JToggleButton("set private");
        } else {
            publicButton = new JToggleButton("set public");
        }
        save = new JButton("Save");
        save.addActionListener(this);

        backgroundPanel = new JPanel();
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(back);
        backgroundPanel.add(quizName);
        backgroundPanel.add(questions);
        setQuestions();
        backgroundPanel.add(addQuestion);
        if (!(user.getUsername().equals("public"))) {
            backgroundPanel.add(publicButton);
        }
        backgroundPanel.add(save);
    }

    // EFFECTS: Adds questions to the edit quiz window
    private void setQuestions() {
        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(50, 100));

        if (quiz.getNumberOfQuestions() == 0) {
            JLabel noQuestions = new JLabel("There are no questions in this quiz.");
            questionPanel.add(noQuestions);
        }

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

    // MODIFIES: quiz
    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof SpecialButton) {
            question = quiz.getQuestion(Integer.parseInt(((SpecialButton) e.getSource()).getName()));
            new WhatToDoWithQuestion(quizSystem, user, quiz, question,
                    Integer.parseInt(((SpecialButton) e.getSource()).getName()), "edit");
            frame.dispose();
        } else if (e.getSource() == back) {
            new WhatToDoWithQuiz(quizSystem, user, quiz);
            frame.dispose();
        } else if (e.getSource() == addQuestion) {
            new CreateQuestion(quizSystem, user, quiz, "edit");
            frame.dispose();
        } else if (e.getSource() == save) {
            quiz.changeName(quizName.getText());
            if (quizIsPublic) {
                if (publicButton.isSelected()) {
                    publicUser.removeQuiz(quiz.getName());
                }
            } else {
                if (publicButton.isSelected()) {
                    publicUser.addQuiz(quiz);
                }
            }
        }
    }
}
