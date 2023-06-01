package me.tapeline.carousellib.icons.items;

import me.tapeline.carousellib.icons.CSquareIcon;
import me.tapeline.carousellib.utils.AdaptableColor;

import java.awt.*;

public class CColorFolderIcon extends CSquareIcon {

    public AdaptableColor color;

    public CColorFolderIcon() {}

    public CColorFolderIcon(AdaptableColor color, int size) {
        super(size);
        this.color = color;
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(color.getColor(darkMode));

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
