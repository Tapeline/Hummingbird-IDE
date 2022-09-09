package me.tapeline.hummingbird.windows.forms.splash;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class SplashScreen extends JWindow {

    public SplashScreen(int w, int h, BufferedImage background) {
        JPanel panel = new JPanel();

        JLabel image = new JLabel(new ImageIcon(background.getScaledInstance(
                w, h, Image.SCALE_SMOOTH
        )));
        panel.add(image);

        add(panel);
        panel.setBackground(new Color(0, 0, 0, 0));
        setBackground(new Color(0, 0, 0, 0));
        setSize(w, h);
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
        requestFocusInWindow();
        toFront();
    }

}