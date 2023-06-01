package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CUpArrowIcon extends CSquareIcon {

    public CUpArrowIcon() {}

    public CUpArrowIcon(int size) {
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
                y + cellSize / 2,
                y + cellSize * 2,
                y + cellSize * 2,
                y + getIconSize(),
                y + getIconSize(),
                y + cellSize * 2,
                y + cellSize * 2,
        }, 7);
    }

}
