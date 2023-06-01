package me.tapeline.carousellib.icons.commonactions;

import me.tapeline.carousellib.icons.CBundledIcon;

import java.awt.*;

public class CAddIcon extends CBundledIcon {

    public CAddIcon() {}

    public CAddIcon(int width, int height) {
        super(width, height);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int centerX = getIconWidth() / 2;
        int centerY = getIconHeight() / 2;
        int vLineWidth = getIconWidth() / 6;
        int hLineWidth = getIconHeight() / 6;

        g.fillRect(x + centerX - vLineWidth / 2, y, vLineWidth, getIconHeight());
        g.fillRect(x, y + centerY - hLineWidth / 2, getIconWidth(), hLineWidth);
    }

}
