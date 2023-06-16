package me.tapeline.carousellib.icons.items;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CShellIcon extends CSquareIcon {

    public CShellIcon() {}

    public CShellIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int cellSize = getIconSize() / 8;

        g.fillPolygon(
                new int[] {
                        x,
                        x + cellSize,
                        x + 5 * cellSize,
                        x + cellSize,
                        x,
                        x + 4 * cellSize
                },
                new int[] {
                        y,
                        y,
                        y + 4 * cellSize,
                        y + 8 * cellSize,
                        y + 8 * cellSize,
                        y + 4 * cellSize,
                },
                6
        );

        g.fillRect(x + 4 * cellSize, y + 7 * cellSize, cellSize * 4, cellSize);
    }

}
