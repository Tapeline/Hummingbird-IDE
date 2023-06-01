package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CDownArrowIcon extends CSquareIcon {

    public CDownArrowIcon() {}

    public CDownArrowIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        int cellSize = getIconSize() / 5;
        g.setColor(primary.getColor(darkMode));

        g.fillPolygon(new int[] {
                x + getIconSize() / 2,
                x + cellSize,
                x + cellSize * 2,
                x + cellSize * 2,
                x + cellSize * 3,
                x + cellSize * 3,
                x + cellSize * 4
        }, new int[] {
                y + getIconSize() - cellSize / 2,
                y + getIconSize() - cellSize * 2,
                y + getIconSize() - cellSize * 2,
                y + getIconSize() - getIconSize(),
                y + getIconSize() - getIconSize(),
                y + getIconSize() - cellSize * 2,
                y + getIconSize() - cellSize * 2,
        }, 7);
    }

}
