package me.tapeline.carousellib.icons.commonactions;

import me.tapeline.carousellib.icons.CSquareIcon;
import me.tapeline.carousellib.utils.AdaptableColor;

import java.awt.*;

public class CPlayIcon extends CSquareIcon {

    public CPlayIcon() {}

    public CPlayIcon(int size) {
        super(size);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(green.getColor(darkMode));

        int centerY = getIconHeight() / 2;

        int t = (int) (getIconWidth() * Math.sqrt(0.3));

        g.fillPolygon(
                new int[] {
                        t / 3 + x,
                        x + 2*t,
                        t / 3 + x
                },
                new int[] {
                        y,
                        y + centerY,
                        y + getIconHeight()
                },
                3
        );
    }

}
