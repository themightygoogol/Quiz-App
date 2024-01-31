package ui;

import model.Quiz;
import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window that shows the results of a quiz that has been completed by the user
 */
public class ResultsPage extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;
    private final Quiz quiz;
    private final int correctAnswersCounter;

    private static JFrame frame;
    private static JPanel panel;

    private final JLabel yourResults;
    private final JButton retry;
    private final JButton mainMenu;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Create new results page window
    @SuppressWarnings("methodlength")
    public ResultsPage(QuizSystem quizSystem, User user, Quiz quiz, int correctAnswersCounter) {
        this.quizSystem = quizSystem;
        this.user = user;
        this.quiz = quiz;
        this.correctAnswersCounter = correctAnswersCounter;

        frame = new JFrame("What to do");
        yourResults = new JLabel("You got " + correctAnswersCounter + "/" + quiz.getNumberOfQuestions()
                + " questions correct");
        retry = new JButton("retry");
        mainMenu = new JButton("mainMenu");

        retry.addActionListener(this);
        mainMenu.addActionListener(this);

        panel = new JPanel();
        setPreferredSize(new Dimension(600, 400));
        panel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(yourResults);
        panel.add(retry);
        panel.add(mainMenu);

        frame.add(panel);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == retry) {
            new NextQuestion(quizSystem, user, quiz, 0, 0);
            frame.dispose();
        } else if (e.getSource() == mainMenu) {
            new MainMenu(quizSystem, user);
            frame.dispose();
        }
    }
}
