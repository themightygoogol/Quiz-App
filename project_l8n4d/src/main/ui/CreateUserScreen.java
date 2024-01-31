package ui;

import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Window for adding a new user to the current quiz system
 */
public class CreateUserScreen extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;

    private static JFrame frame;
    private static JPanel backgroundPanel;
    private static JScrollPane backgroundScrPane;

    private final JButton goBack;
    private final JLabel newUser;
    private final JLabel username;
    private final JTextField daUsername;
    private final JTextField daPassword;
    private final JButton create;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;


    // EFFECTS: Opens new create user window
    @SuppressWarnings("methodlength")
    public CreateUserScreen(QuizSystem quizSystem) {
        this.quizSystem = quizSystem;

        frame = new JFrame("Create New User Menu");
        goBack = new JButton("back");
        newUser = new JLabel("New user:");
        username = new JLabel("Username:");
        daUsername = new JTextField("username");
        JLabel password = new JLabel("Password");
        daPassword = new JTextField("password");
        create = new JButton("create");
        create.addActionListener(this);
        goBack.addActionListener(this);

        backgroundPanel = new JPanel();
        backgroundScrPane = new JScrollPane(backgroundPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setPreferredSize(new Dimension(400, 400));
        add(backgroundScrPane, BorderLayout.CENTER);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.add(goBack);
        backgroundPanel.add(newUser);
        backgroundPanel.add(username);
        backgroundPanel.add(daUsername);
        backgroundPanel.add(password);
        backgroundPanel.add(daPassword);
        backgroundPanel.add(create);

        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // MODIFIES: quizSystem
    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            if (quizSystem.findUser(daUsername.getText()) == null) {
                User user = new User(daUsername.getText(), daPassword.getText(), new ArrayList<>());
                quizSystem.addUser(user);
                new StartMenu(quizSystem);
                frame.dispose();
            }
        } else if (e.getSource() == goBack) {
            new StartMenu(quizSystem);
            frame.dispose();
        }
    }
}
