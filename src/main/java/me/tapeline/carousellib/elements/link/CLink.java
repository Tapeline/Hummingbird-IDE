package me.tapeline.carousellib.elements.link;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class CLink extends JLabel {

    public static Color linkColor = new Color(44, 117, 180);
    public static Color linkHoverColor = new Color(38, 93, 141);

    private List<ActionListener> listeners = new ArrayList<>();

    public CLink(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        hook();
    }

    public CLink(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        hook();
    }

    public CLink(String text) {
        super(text);
        hook();
    }

    public CLink(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        hook();
    }

    public CLink(Icon image) {
        super(image);
        hook();
    }

    public CLink() {
        hook();
    }

    public void addLinkListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeLinkListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public List<ActionListener> getLinkListeners() {
        return listeners;
    }

    private void hook() {
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setForeground(linkColor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (ActionListener listener : listeners)
                    listener.actionPerformed(new ActionEvent(
                            e, ActionEvent.ACTION_PERFORMED, "click"
                    ));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(linkHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(linkColor);
            }
        });
    }

}
