package me.tapeline.carousellib.icons.misc;

import me.tapeline.carousellib.icons.CBundledIcon;
import me.tapeline.carousellib.utils.AdaptableColor;

import java.awt.*;

public class CSymbolicIcon extends CBundledIcon {

    private String symbol;
    private AdaptableColor color;
    private Font font;

    public CSymbolicIcon(String symbol, AdaptableColor color) {
        this.symbol = symbol;
        this.color = color;
    }

    public CSymbolicIcon(int width, int height, String symbol, AdaptableColor color) {
        super(width, height);
        this.symbol = symbol;
        this.color = color;
    }

    public CSymbolicIcon(String symbol, AdaptableColor color, Font font) {
        this.symbol = symbol;
        this.color = color;
        this.font = font;
    }

    public CSymbolicIcon(int width, int height, String symbol, AdaptableColor color, Font font) {
        super(width, height);
        this.symbol = symbol;
        this.color = color;
        this.font = font;
    }

    @Override
    public void paintCustomIcon(Component c, Graphics g, int x, int y) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color.getColor(darkMode));
        g.fillRoundRect(x, y, getIconWidth(), getIconWidth(),
                getIconWidth() / 6, getIconHeight() / 6);
        g.setColor(primary.getColor(darkMode));
        if (font != null) {
            Font prev = g.getFont();
            g.setFont(font);
            g.drawString(symbol, x, y + getIconHeight());
            g.setFont(prev);
        } else g.drawString(symbol, x, y + getIconHeight());
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public AdaptableColor getColor() {
        return color;
    }

    public void setColor(AdaptableColor color) {
        this.color = color;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

}
