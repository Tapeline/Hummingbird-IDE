package me.tapeline.hummingbird.expansions.colorschemes;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class TextStyle {

    public Color foreground = null;
    public Boolean underline = null;
    public Boolean strikethrough = null;
    public Color background = null;
    public Boolean italic = null;
    public Boolean subscript = null;
    public Boolean superscript = null;
    public Boolean bold = null;
    public String fontFamily = null;
    public Integer fontSize = null;
    
    public TextStyle() {}

    public TextStyle fg(Color c) {
        foreground = c;
        return this;
    }

    public TextStyle bg(Color c) {
        background = c;
        return this;
    }

    public TextStyle family(String f) {
        fontFamily = f;
        return this;
    }

    public TextStyle size(Integer i) {
        fontSize = i;
        return this;
    }

    public TextStyle bold() {
        if (bold == null) bold = true;
        bold = !bold;
        return this;
    }

    public TextStyle italic() {
        if (italic == null) italic = true;
        italic = !italic;
        return this;
    }

    public TextStyle under() {
        if (underline == null) underline = true;
        underline = !underline;
        return this;
    }

    public TextStyle strike() {
        if (strikethrough == null) strikethrough = true;
        strikethrough = !strikethrough;
        return this;
    }

    public TextStyle below() {
        if (subscript == null) subscript = true;
        subscript = !subscript;
        return this;
    }

    public TextStyle above() {
        if (superscript == null) superscript = true;
        superscript = !superscript;
        return this;
    }

    public SimpleAttributeSet attributeSet() {
        SimpleAttributeSet s = new SimpleAttributeSet();
        if (foreground != null)
            StyleConstants.setForeground(s, foreground);
        if (background != null)
            StyleConstants.setBackground(s, background);
        if (this.fontFamily != null)
            StyleConstants.setFontFamily(s, fontFamily);
        if (this.fontSize != null)
            StyleConstants.setFontSize(s, fontSize);
        if (this.bold != null)
            StyleConstants.setBold(s, bold);
        if (this.italic != null)
            StyleConstants.setItalic(s, italic);
        if (this.strikethrough != null)
            StyleConstants.setStrikeThrough(s, strikethrough);
        if (this.subscript != null)
            StyleConstants.setSubscript(s, subscript);
        if (this.superscript != null)
            StyleConstants.setSuperscript(s, superscript);
        if (this.underline != null)
            StyleConstants.setUnderline(s, underline);
        return s;
    }

}
