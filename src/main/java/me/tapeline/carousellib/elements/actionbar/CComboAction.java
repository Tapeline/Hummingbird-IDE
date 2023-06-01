package me.tapeline.carousellib.elements.actionbar;

import javax.swing.*;
import java.awt.*;

public class CComboAction extends CAbstractAction {

    private final JComboBox<?> comboBox;

    public CComboAction(JComboBox<?> comboBox) {
        this.comboBox = comboBox;
    }

    public JComboBox<?> getComboBox() {
        return comboBox;
    }

    @Override
    public Component buildComponent() {
        return comboBox;
    }

}
