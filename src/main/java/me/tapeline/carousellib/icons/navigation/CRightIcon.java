package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CRightIcon extends CSquareIcon {

    public CRightIcon() {}

    public CRightIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int centerX = getIconWidth() / 2;
        int centerY = getIconHeight() / 2;
        int triangleH = getIconHeight() / 2;

        int[] tx = {
                x + getIconSize() - (centerX + triangleH / 2),
                x + getIconSize() - (centerX - triangleH / 2),
                x + getIconSize() - (centerX + triangleH / 2),
        };
        int[] ty = {
                y,
                y + centerY,
                y + getIconWidth()
        };

        g.fillPolygon(tx, ty, 3);

    }

}
