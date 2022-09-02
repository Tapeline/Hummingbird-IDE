package me.tapeline.hummingbird.expansions.syntaxchecker;

import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;
import me.tapeline.hummingbird.expansions.highlighter.Bounds;

public class SyntaxTip {

    public Bounds bounds;
    public TextStyle style;

    public SyntaxTip(Bounds bounds) {
        this.bounds = bounds;
    }
}
