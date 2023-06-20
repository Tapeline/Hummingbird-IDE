package me.tapeline.hummingbird.ide.ui.syntaxtextarea;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Style;
import org.fife.ui.rsyntaxtextarea.TokenMaker;

import java.awt.*;

public class HSyntaxTextArea extends RSyntaxTextArea {

    public HSyntaxTextArea() {}

    public HSyntaxTextArea(RSyntaxDocument doc) {
        super(doc);
    }

    public HSyntaxTextArea(String text) {
        super(text);
    }

    public HSyntaxTextArea(int rows, int cols) {
        super(rows, cols);
    }

    public HSyntaxTextArea(String text, int rows, int cols) {
        super(text, rows, cols);
    }

    public HSyntaxTextArea(RSyntaxDocument doc, String text, int rows, int cols) {
        super(doc, text, rows, cols);
    }

    public HSyntaxTextArea(int textMode) {
        super(textMode);
    }

    public void setTokenMaker(TokenMaker tokenMaker) {
        RSyntaxDocument doc = (RSyntaxDocument)getDocument();
        String oldStyle = getSyntaxEditingStyle();
        ((RSyntaxDocument) getDocument()).setSyntaxStyle(tokenMaker);
        try {
            FieldUtils.writeField(
                    this,
                    "syntaxStyleKey",
                    "text/unknown",
                    true
            );
        } catch (IllegalAccessException e) { e.printStackTrace(); }
        firePropertyChange(SYNTAX_STYLE_PROPERTY, oldStyle, "text/unknown");
        setActiveLineRange(-1, -1);
    }

}
