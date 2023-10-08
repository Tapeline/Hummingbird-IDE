package me.tapeline.hummingbird.ide.expansion.syntax;

import me.tapeline.hummingbird.ide.utils.Bounds;

import javax.swing.*;

public class CompletionSuggestion {

    private Icon icon;
    private String text;
    private String inlineDescription;
    private int relevance;
    private Bounds bounds;

    public CompletionSuggestion(Icon icon, String text, String inlineDescription, int relevance,
                                Bounds bounds) {
        this.icon = icon;
        this.text = text;
        this.inlineDescription = inlineDescription;
        this.relevance = relevance;
        this.bounds = bounds;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public String getInlineDescription() {
        return inlineDescription;
    }

    public int getRelevance() {
        return relevance;
    }

    public Bounds getBounds() {
        return bounds;
    }

}
