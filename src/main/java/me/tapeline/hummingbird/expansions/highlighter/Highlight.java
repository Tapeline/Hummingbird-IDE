package me.tapeline.hummingbird.expansions.highlighter;

import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;

public class Highlight {

    public Bounds bounds;
    public TextStyle style;

    public Highlight(Bounds bounds, TextStyle style) {
        this.bounds = bounds;
        this.style = style;
    }
}
