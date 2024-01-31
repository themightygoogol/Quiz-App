package ui;

import model.QuizSystem;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window allowing for edits on an existing user
 */
public class EditUser extends JFrame implements ActionListener {
    private final QuizSystem quizSystem;
    private final User user;

    private static JFrame frame;
    private static JPanel backgroundPanel;
    private static JScrollPane backgroundScrPane;

    private final JButton goBack;
    private final JLabel newUser;
    private final JLabel newUsername;
    private final JTextField daUsername;
    private final JLabel newPassword;
    private final JTextField daPassword;
    private final JButton update;

    private static final int TOP_SIZE = 100;
    private static final int LEFT_SIZE = 100;
    private static final int BOTTOM_SIZE = 100;
    private static final int RIGHT_SIZE = 100;

    // EFFECTS: Creates new edit user window
    @SuppressWarnings("methodlength")
    public EditUser(QuizSystem quizSystem, User user) {
        this.quizSystem = quizSystem;
        this.user = user;

        frame = new JFrame("Edit User Menu");
        goBack = new JButton("back");
        newUser = new JLabel("Editing " + user.getUsername());
        newUsername = new JLabel("New username:");
        daUsername = new JTextField(user.getUsername());
        newPassword = new JLabel("New password");
        daPassword = new JTextField(user.getPassword());
        update = new JButton("update");
        update.addActionListener(this);
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
        backgroundPanel.add(newUsername);
        backgroundPanel.add(daUsername);
        backgroundPanel.add(newPassword);
        backgroundPanel.add(daPassword);
        backgroundPanel.add(update);

        frame.add(backgroundPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    // MODIFIES: user
    // EFFECTS: Create new window based on button pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == update) {
            user.setUsername(daUsername.getText());
            user.setPassword(daPassword.getText());
            new WhatToDoWithUser(quizSystem, user);
            frame.dispose();
        } else if (e.getSource() == goBack) {
            new WhatToDoWithUser(quizSystem, user);
            frame.dispose();
        }
    }
}
