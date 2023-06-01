package me.tapeline.carousellib.hooks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonRolloverAreaFiller extends MouseAdapter {

    public void mouseEntered(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof AbstractButton button) {
            button.setContentAreaFilled(true);
        }
    }

    public void mouseExited(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof AbstractButton button) {
            button.setContentAreaFilled(false);
        }
    }

}
