package me.tapeline.carousellib.icons.commonactions;

import me.tapeline.carousellib.icons.CBundledIcon;

import java.awt.*;

public class CRemoveIcon extends CBundledIcon {

    public CRemoveIcon() {}

    public CRemoveIcon(int width, int height) {
        super(width, height);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(primary.getColor(darkMode));

        int centerY = getIconHeight() / 2;
        int hLineWidth = getIconHeight() / 6;

        g.fillRect(x, y + centerY - hLineWidth / 2, getIconWidth(), hLineWidth);
    }

}
