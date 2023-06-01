package me.tapeline.hummingbird.ide.frames.splash;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SplashScreen extends JWindow {

    public SplashScreen(BufferedImage background) {
        JPanel panel = new JPanel();

        int w = background.getWidth();
        int h = background.getHeight();

        JLabel image = new JLabel(new ImageIcon(background.getScaledInstance(
                w, h, Image.SCALE_SMOOTH
        )));
        panel.add(image);
        panel.setBackground(new Color(0, 0, 0, 0));

        add(panel);

        setBackground(new Color(0, 0, 0, 0));
        setSize(w, h);
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
        requestFocusInWindow();
        toFront();
    }

}
