package me.tapeline.carousellib.icons;

import me.tapeline.carousellib.utils.AdaptableColor;

import javax.swing.*;
import java.awt.*;

public abstract class CBundledIcon implements Icon {

    public static final AdaptableColor primary = new AdaptableColor(
            new Color(49, 51, 54),
            new Color(134, 146, 154)
    );

    public static final int DEFAULT_WIDTH = 32;
    public static final int DEFAULT_HEIGHT = 32;

    private int width;
    private int height;
    private boolean antialiasingEnabled;
    
    public static boolean darkMode = true;
    public static boolean antialiasingByDefault = false;

    public CBundledIcon() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public CBundledIcon(int width, int height) {
        this(width, height, antialiasingByDefault);
    }

    public CBundledIcon(int width, int height, boolean antialias) {
        this.width = width;
        this.height = height;
        this.antialiasingEnabled = antialias;
    }

    public boolean isAntialiasingEnabled() {
        return antialiasingEnabled;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (antialiasingEnabled)
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        paintCustomIcon(c, g, x, y);
    }

    public abstract void paintCustomIcon(Component c, Graphics g, int x, int y);

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

}
