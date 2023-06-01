package me.tapeline.carousellib.elements.closabletabs;

import me.tapeline.carousellib.hooks.ButtonRolloverAreaFiller;
import me.tapeline.carousellib.icons.navigation.CCloseIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CloseTabButton extends JButton implements ActionListener {

    protected CClosableTabbedPane pane;
    protected ClosableTab tab;

    public CloseTabButton(CClosableTabbedPane pane, ClosableTab tab) {
        this.pane = pane;
        this.tab = tab;
        int size = 17;
        setIcon(new CCloseIcon(8, 8));
        setPreferredSize(new Dimension(size, size));
        setToolTipText("close this tab");
        setContentAreaFilled(false);
        setFocusable(false);
        setBorder(BorderFactory.createEtchedBorder());
        setBorderPainted(false);
        addMouseListener(new ButtonRolloverAreaFiller());
        setRolloverEnabled(true);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        int i = pane.indexOfTabComponent(tab);
        if (i != -1) {
            for (TabCloseListener listener : pane.getTabCloseListeners())
                listener.tabClosed(new TabCloseEvent(this, tab));
            pane.remove(i);
        }
    }

    /*public void updateUI() { }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        //shift the image for pressed buttons
        if (getModel().isPressed()) {
            g2.translate(1, 1);
        }
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        if (getModel().isRollover()) {
            g2.setColor(Color.MAGENTA);
        }
        int delta = 6;
        g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
        g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
        g2.dispose();
    }*/
}