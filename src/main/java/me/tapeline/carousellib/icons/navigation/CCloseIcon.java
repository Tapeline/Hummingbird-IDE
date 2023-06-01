package me.tapeline.carousellib.icons.navigation;

import me.tapeline.carousellib.icons.CBundledIcon;

import java.awt.*;

public class CCloseIcon extends CBundledIcon {

    public CCloseIcon() {}

    public CCloseIcon(int width, int height) {
        super(width, height);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int centerX = getIconWidth() / 2;
        int centerY = getIconHeight() / 2;
        int vLineWidth = getIconWidth() / 4;
        int hLineWidth = getIconHeight() / 4;
        int avgWidth = (vLineWidth + hLineWidth) / 2;
        int margin = (int) (avgWidth / Math.sqrt(2));

        int[] p1x = {
                x,
                x + getIconWidth() - margin,
                x + getIconWidth(),
                x + margin
        };
        int[] p1y = {
                y + margin,
                y + getIconHeight(),
                y + getIconHeight() - margin,
                y
        };

        g.fillPolygon(p1x, p1y, 4);

        int[] p2x = {
                x + getIconWidth() - margin,
                x,
                x + margin,
                x + getIconWidth()
        };
        int[] p2y = {
                y,
                y + getIconHeight() - margin,
                y + getIconHeight(),
                y + margin
        };

        g.fillPolygon(p2x, p2y, 4);

    }

}
