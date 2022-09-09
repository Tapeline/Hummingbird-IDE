package me.tapeline.hummingbird.expansions.syntaxchecker;

import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;
import me.tapeline.hummingbird.expansions.highlighter.Bounds;

public class SyntaxTip {

    public enum TipType {
        ERROR,
        WARNING,
        CODE_STYLE,
        DEPRECATION
    }

    public String tip;

    public Bounds bounds;
    public TipType type;

    public SyntaxTip(Bounds bounds, String tip, TipType type) {
        this.bounds = bounds;
        this.type = type;
        this.tip = tip;
    }

    public TextStyle style() {
        switch (type) {
            case ERROR:
                return Registry.currentTheme.scheme().error;
            case WARNING:
                return Registry.currentTheme.scheme().warning;
            case DEPRECATION:
                return Registry.currentTheme.scheme().deprecated;
            case CODE_STYLE:
                return Registry.currentTheme.scheme().codeStyleWarning;
        }
        return Registry.currentTheme.scheme().warning;
    }
}
