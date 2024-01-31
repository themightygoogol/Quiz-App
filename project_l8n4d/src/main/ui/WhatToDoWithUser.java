package ui;

import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window offering operations on a selected user
 */
public class WhatToDoWithUser extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;

    private static JFrame frame;
    private static JPanel panel;

    private final JButton goBack;
    private final JLabel whatToDo;
    private final JButton login;
    private final JButton edit;
    private final JButton delete;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Creates new WhatToDoWithUser window
    @SuppressWarnings("methodlength")
    public WhatToDoWithUser(QuizSystem quizSystem, User user) {
        this.quizSystem = quizSystem;
        this.user = user;

        frame = new JFrame("What to do");
        goBack = new JButton("back");
        whatToDo = new JLabel(user.getUsername() + " selected");
        login = new JButton("login");
        edit = new JButton("edit");
        delete = new JButton("delete");

        login.addActionListener(this);
        edit.addActionListener(this);
        delete.addActionListener(this);
        goBack.addActionListener(this);

        panel = new JPanel();
        setPreferredSize(new Dimension(400, 400));
        panel.setBorder(BorderFactory.createEmptyBorder(TOP_SIZE, LEFT_SIZE, BOTTOM_SIZE, RIGHT_SIZE));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(goBack);
        panel.add(whatToDo);
        panel.add(login);
        panel.add(edit);
        panel.add(delete);

        frame.add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // MODIFIES: quizSystem
    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
            new LoginScreen(quizSystem, user);
            frame.dispose();
        } else if (e.getSource() == edit) {
            new EditUser(quizSystem, user);
            frame.dispose();
        } else if (e.getSource() == delete) {
            quizSystem.removeUser(user.getUsername());
            new StartMenu(quizSystem);
            frame.dispose();
        } else if (e.getSource() == goBack) {
            new StartMenu(quizSystem);
            frame.dispose();
        }
    }

}
