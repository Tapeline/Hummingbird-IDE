package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;
import java.util.function.BiConsumer;

public class CButtonAction extends CAbstractAction {

    private String text;
    private Icon icon;
    private BiConsumer<CAbstractAction, JComponent> onAction;

    public CButtonAction(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public CButtonAction(String text, Icon icon, BiConsumer<CAbstractAction, JComponent> onAction) {
        this(text, icon);
        this.onAction = onAction;
    }

    public BiConsumer<CAbstractAction, JComponent> getOnAction() {
        return onAction;
    }

    public void setOnAction(BiConsumer<CAbstractAction, JComponent> onAction) {
        this.onAction = onAction;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    @Override
    public JComponent buildComponent() {
        JButton actionButton = new JButton();
        if (text != null)
            actionButton.setText(text);
        if (icon != null)
            actionButton.setIcon(icon);
        if (onAction != null)
            actionButton.addActionListener((e) -> {
                    onAction.accept(this, actionButton);
            });
        customize(actionButton);
        return actionButton;
    }

}
