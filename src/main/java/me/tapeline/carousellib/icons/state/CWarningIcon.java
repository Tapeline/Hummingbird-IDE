package me.tapeline.carousellib.icons.state;

import me.tapeline.carousellib.icons.CBundledIcon;
import me.tapeline.carousellib.utils.AdaptableColor;

import java.awt.*;

public class CWarningIcon extends CBundledIcon {

    public static AdaptableColor yellow = new AdaptableColor(
            new Color(0xA68E35),
            new Color(0xBC9018)
    );
    public static AdaptableColor inner = new AdaptableColor(
            new Color(0xFFFFFF),
            new Color(0xC0C0C0)
    );

    public CWarningIcon() {}

    public CWarningIcon(int width, int height) {
        super(width, height);
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        g.setColor(yellow.getColor(darkMode));

        int centerX = getIconWidth() / 2;
        int centerY = getIconHeight() / 2;
        int vLineWidth = getIconWidth() / 8;

        int[] tx = {
                x,
                x + centerX,
                x + getIconWidth()
        };
        int[] ty = {
                y + getIconHeight() - getIconHeight() / 10,
                y + getIconHeight() / 10,
                y + getIconHeight() - getIconHeight() / 10,
        };
        g.fillPolygon(tx, ty, 3);

        g.setColor(inner.getColor(darkMode));

        g.fillRoundRect(
                x + centerX - vLineWidth / 2,
                y + getIconHeight() / 3,
                vLineWidth,
                getIconHeight() / 3,
                getIconWidth() / 16,
                getIconHeight() / 16
        );

        g.fillOval(
                x + centerX - vLineWidth / 2,
                y + getIconHeight() / 8 * 6,
                vLineWidth, vLineWidth
        );
    }

}
