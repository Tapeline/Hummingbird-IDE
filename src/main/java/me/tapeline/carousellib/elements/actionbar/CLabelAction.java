package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;

public class CLabelAction extends CAbstractAction {

    private String text;
    private Icon icon;

    public CLabelAction(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
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
        JLabel label = new JLabel();
        if (text != null)
            label.setText(text);
        if (icon != null)
            label.setIcon(icon);
        return label;
    }

}
