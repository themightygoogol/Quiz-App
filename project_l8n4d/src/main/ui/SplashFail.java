package ui;

import javax.swing.*;

/**
 * A window with a picture that shows an icon with the word 'fail'
 */
public class SplashFail extends JFrame {
    private static JFrame frame;
    private static JPanel panel;

    private Timer timer;
    private final int delay;
    private final ImageIcon passIcon;
    private final JLabel labelForPicture;

    // EFFECTS: Create window with fail icon
    public SplashFail() {
        delay = 3000;
        passIcon = new ImageIcon("./data/failIcon.png");
        labelForPicture = new JLabel(passIcon);


        frame = new JFrame();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(225,225,225,225);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(labelForPicture);

        frame.add(panel);
        frame.setSize(225, 250);
        frame.setVisible(true);
    }
}
