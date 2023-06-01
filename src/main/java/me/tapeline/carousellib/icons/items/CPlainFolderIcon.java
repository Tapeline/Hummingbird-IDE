package me.tapeline.carousellib.icons.items;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CPlainFolderIcon extends CSquareIcon {

    public CPlainFolderIcon() {}

    public CPlainFolderIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int cellSize = getIconSize() / 5;

        g.fillPolygon(
                new int[] {
                        x,
                        x + 2 * cellSize,
                        x + 3 * cellSize,
                        x + 5 * cellSize,
                        x + 5 * cellSize,
                        x
                },
                new int[] {
                        y + cellSize,
                        y + cellSize,
                        y + 2 * cellSize,
                        y + 2 * cellSize,
                        y + 5 * cellSize,
                        y + 5 * cellSize,
                },
                6
        );
    }

}
