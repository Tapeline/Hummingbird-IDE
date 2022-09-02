package me.tapeline.hummingbird.ui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class JComboBoxWIthOptions extends JComboBox {

    public List<ComboBoxOptionListener> optionListeners = new ArrayList<>();

    public void addOptionListener(ComboBoxOptionListener l) {
        optionListeners.add(l);
    }

}
