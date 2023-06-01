package me.tapeline.carousellib.elements.closabletabs;

import javax.swing.*;
import java.awt.*;

public class ClosableTab extends JPanel {

    protected final CClosableTabbedPane pane;
    protected final Component boundComponent;

    public ClosableTab(final CClosableTabbedPane pane, final Component component) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.boundComponent = component;
        if (pane == null)
            throw new NullPointerException("TabbedPane is null");
        this.pane = pane;
        setOpaque(false);

        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(ClosableTab.this);
                if (i != -1) return pane.getTitleAt(i);
                return null;
            }
        };

        add(label);

        CloseTabButton button = new CloseTabButton(pane, this);
        add(button);

        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }

    public CClosableTabbedPane getPane() {
        return pane;
    }

    public Component getBoundComponent() {
        return boundComponent;
    }

}