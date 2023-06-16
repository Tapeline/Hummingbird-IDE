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

        g.fillPolygon(
                new int[] {
                        x,
                        x + getIconWidth(),
                        x
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
