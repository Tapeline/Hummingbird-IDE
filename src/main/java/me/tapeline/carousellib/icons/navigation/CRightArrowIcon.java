package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CRightArrowIcon extends CSquareIcon {

    public CRightArrowIcon() {}

    public CRightArrowIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        int cellSize = getIconSize() / 5;
        g.setColor(primary.getColor(darkMode));

        g.fillPolygon(new int[] {
                x + getIconSize() - cellSize / 2,
                x + getIconSize() - cellSize * 2,
                x + getIconSize() - cellSize * 2,
                x + getIconSize() - getIconSize(),
                x + getIconSize() - getIconSize(),
                x + getIconSize() - cellSize * 2,
                x + getIconSize() - cellSize * 2,
        }, new int[] {
                y + getIconSize() / 2,
                y + cellSize,
                y + cellSize * 2,
                y + cellSize * 2,
                y + cellSize * 3,
                y + cellSize * 3,
                y + cellSize * 4
        }, 7);
    }

}
