package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CBundledIcon;
import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CUpIcon extends CSquareIcon {

    public CUpIcon() {}

    public CUpIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int centerX = getIconWidth() / 2;
        int centerY = getIconHeight() / 2;
        int triangleH = getIconHeight() / 2;

        int[] tx = {
                x,
                x + centerX,
                x + getIconWidth()
        };
        int[] ty = {
                y + centerY + triangleH / 2,
                y + centerY - triangleH / 2,
                y + centerY + triangleH / 2,
        };

        g.fillPolygon(tx, ty, 3);

    }

}
