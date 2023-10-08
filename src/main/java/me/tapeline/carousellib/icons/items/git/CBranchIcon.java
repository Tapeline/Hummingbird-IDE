package me.tapeline.carousellib.icons.items.git;

import me.tapeline.carousellib.icons.CSquareIcon;

import java.awt.*;

public class CBranchIcon extends CSquareIcon {

    private boolean isActive;

    public CBranchIcon(int size, boolean isActive) {
        super(size);
        this.isActive = isActive;
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        int notch = getIconSize() / 6;
        int thickness = getIconSize() / 12;
        g.setColor((isActive? primary : yellow).getColor(darkMode));
        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        g.drawLine(x + notch * 2, y + notch,
                x + notch * 2, y + getIconSize());
        g.drawLine(x + notch * 2, y + getIconSize(),
                x + notch * 4, y + notch * 3);
        drawCircle(g, x + notch * 2, y + notch, thickness);
        drawCircle(g, x + notch * 2, y + getIconSize(), thickness);
        drawCircle(g, x + notch * 4, y + notch * 3, thickness);
    }

    private void drawCircle(Graphics g, int xC, int yC, int r) {
        g.drawOval(xC - r, yC - r, r*2, r*2);
    }

}
