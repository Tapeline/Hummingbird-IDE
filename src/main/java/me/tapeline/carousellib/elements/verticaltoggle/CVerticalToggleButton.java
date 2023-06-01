package me.tapeline.carousellib.elements.verticaltoggle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CVerticalToggleButton extends JToggleButton {

    private final XButton template;
    private boolean clockwise;

    public CVerticalToggleButton(Icon icon, String text, boolean clockwise) {
        super();
        template = new XButton(icon, text, getFont());
        this.clockwise = clockwise;
        updateDimensions();
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setContentAreaFilled(true);
            }

            public void mouseExited(MouseEvent e) {
                setContentAreaFilled(isSelected());
            }
        });
    }

    public void setFont(Font font) {
        super.setFont(font);
        if (template != null) {
            template.setFont(font);
        }
    }

    public void updateDimensions() {
        Dimension d = template.getPreferredSize();
        setMaximumSize(new Dimension(d.height, d.width));
        setPreferredSize(new Dimension(d.height, d.width));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        Dimension d = getPreferredSize();
        template.setSize(d.height, d.width);

        if (clockwise) {
            g2.rotate(Math.PI / 2.0);
            g2.translate(0, -getSize().width);
        } else {
            g2.translate(0, getSize().height);
            g2.rotate(-Math.PI / 2.0);
        }
        template.setSelected(getModel().isSelected());
        template.setContentAreaFilled(isContentAreaFilled());
        template.paintComponent(g2);
        g2.dispose();
    }

    public boolean isClockwise() {
        return clockwise;
    }

    public void setClockwise(boolean clockwise) {
        this.clockwise = clockwise;
    }

    public static class XButton extends JToggleButton {
        public XButton(Icon icon, String text, Font font) {
            super(text, icon);
            setFont(font);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }

}
