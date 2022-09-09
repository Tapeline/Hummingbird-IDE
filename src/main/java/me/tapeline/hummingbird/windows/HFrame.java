package me.tapeline.hummingbird.windows;

import javax.swing.*;

public abstract class HFrame extends JFrame {

    public HFrame(String s) {
        super(s);
    }

    public abstract void reloadFromConfig();

}
