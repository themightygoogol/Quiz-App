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
 * Create question window allows user to create a new question
 */
public class CreateQuestion extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private final Quiz quiz;
    private Question question;
    private final String origin;

    private static JFrame frame;
    private static JPanel backgroundPanel;

    private final JButton goBack;
    private final JLabel newQuestion;
    private final JTextField daQuestion;
    private final JLabel newAnswer;
    private final JTextField daAnswer;
    private final JButton add;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // REQUIRES: origin must be 'edit' or 'create'
    // EFFECTS: opens new create question window
    @SuppressWarnings("methodlength")
    public CreateQuestion(QuizSystem quizSystem, User user, Quiz quiz, String origin) {
        this.quizSystem = quizSystem;
        this.user = user;
        this.quiz = quiz;
        this.origin = origin;


        frame = new JFrame("New Question Menu");
        goBack = new JButton("back");
        newQuestion = new JLabel("New question:");
        daQuestion = new JTextField();
        newAnswer = new JLabel("New answer");
        daAnswer = new JTextField();
        add = new JButton("Add");
        add.addActionListener(this);
        goBack.addActionListener(this);

        backgroundPanel = new JPanel();
        setPreferredSize(new Dimension(400, 400));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(goBack);
        backgroundPanel.add(newQuestion);
        backgroundPanel.add(daQuestion);
        backgroundPanel.add(newAnswer);
        backgroundPanel.add(daAnswer);
        backgroundPanel.add(add);

        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // MODIFIES: quiz
    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            question = new Question(daQuestion.getText(), daAnswer.getText());
            quiz.addQuestion(question);
            if (origin.equals("create")) {
                new CreateQuiz(quizSystem, user, quiz);
                frame.dispose();
            } else if (origin.equals("edit")) {
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
