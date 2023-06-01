package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CPackageIcon extends CSquareIcon {

    public CPackageIcon() {}

    public CPackageIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int cellSize = getIconSize() / 5;

        g.fillRect(x, y, cellSize * 2, cellSize * 2);
        g.fillRect(x + cellSize * 3, y, cellSize * 2, cellSize * 2);
        g.fillRect(x, y + cellSize * 3, cellSize * 2, cellSize * 2);
        g.fillRect(x + cellSize * 3, y + cellSize * 3, cellSize * 2, cellSize * 2);
    }

}
