package me.tapeline.hummingbird.styling.colorschemes;

import me.tapeline.hummingbird.resources.Fonts;

import java.awt.*;

public class TextStyle {

    public Color foreground = new Color(0xFFFFFF);
    public Boolean underline = null;
    public Boolean strikethrough = null;
    public Color background = null;
    public Boolean italic = null;
    public Boolean subscript = null;
    public Boolean superscript = null;
    public Boolean bold = null;
    public Font font = null;
    
    public TextStyle() {}

    public TextStyle fg(Color c) {
        foreground = c;
        return this;
    }

    public TextStyle bg(Color c) {
        background = c;
        return this;
    }

    public TextStyle font(Font f) {
        font = f;
        return this;
    }

    public TextStyle bold() {
        bold = !bold;
        return this;
    }

    public TextStyle italic() {
        italic = !italic;
        return this;
    }

    public TextStyle under() {
        underline = !underline;
        return this;
    }

    public TextStyle strike() {
        strikethrough = !strikethrough;
        return this;
    }

    public TextStyle below() {
        subscript = !subscript;
        return this;
    }

    public TextStyle above() {
        superscript = !superscript;
        return this;
    }

}
