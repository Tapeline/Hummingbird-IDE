package me.tapeline.carousellib.icons.items;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CPlainFileIcon extends CSquareIcon {

    public CPlainFileIcon() {}

    public CPlainFileIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int cellSize = getIconSize() / 5;

        g.fillRect(x + cellSize, y + cellSize * 2, cellSize * 3, cellSize * 3);
        g.fillRect(x + cellSize * 3, y, cellSize, cellSize * 2);

        g.fillPolygon(
                new int[] {
                        x + ((int) ((double) cellSize * 2.8)),
                        x + ((int) ((double) cellSize * 2.8)),
                        x + cellSize,
                },
                new int[] {
                        y,
                        y + ((int) ((double) cellSize * 1.8)),
                        y + ((int) ((double) cellSize * 1.8))
                },
                3
        );
    }

}
