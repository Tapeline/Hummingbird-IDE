package me.tapeline.hummingbird.ide.frames;

import me.tapeline.hummingbird.ide.Application;

import javax.swing.*;
import java.awt.*;

public abstract class AppWindow extends JFrame {

    public AppWindow(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void addToActive() {
        Application.instance.addWindow(this);
    }

    public void removeFromActive() {
        Application.instance.removeWindow(this);
    }

    public void centerOnScreen() {
        setLocationRelativeTo(null);
    }

    public void dispose() {
        super.dispose();
        Application.instance.saveConfig();
    }

}
