package me.tapeline.carousellib.icons.commonactions;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CStopIcon extends CSquareIcon {

    public CStopIcon() {}

    public CStopIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(red.getColor(darkMode));

        g.fillRect(x, y, getIconWidth(), getIconHeight());
    }

}
