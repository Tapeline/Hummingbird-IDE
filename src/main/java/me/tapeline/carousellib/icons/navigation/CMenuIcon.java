package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CMenuIcon extends CSquareIcon {

    public CMenuIcon() {}

    public CMenuIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int cellSize = getIconSize() / 8;

        g.fillRect(x, y, getIconSize(), cellSize * 2);
        g.fillRect(x, y + cellSize * 3, getIconSize(), cellSize * 2);
        g.fillRect(x, y + cellSize * 6, getIconSize(), cellSize * 2);
    }

}
