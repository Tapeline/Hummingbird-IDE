package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CBundledIcon;
import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CDownIcon extends CSquareIcon {

    public CDownIcon() {}

    public CDownIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int centerX = getIconWidth() / 2;
        int centerY = getIconHeight() / 2;
        int triangleH = getIconHeight() / 2;
        int slopeSideLen = (int) (Math.sqrt(getIconWidth() / 2) + Math.sqrt(triangleH));

        int[] tx = {
                x,
                x + centerX,
                x + getIconWidth()
        };
        int[] ty = {
                y + centerY - triangleH / 2,
                y + centerY + triangleH / 2,
                y + centerY - triangleH / 2,
        };

        g.fillPolygon(tx, ty, 3);

    }

}
