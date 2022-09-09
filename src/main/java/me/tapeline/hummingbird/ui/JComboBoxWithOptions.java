package me.tapeline.hummingbird.ui;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JComboBoxWithOptions<T> extends JComboBox<T> {

    public List<ComboBoxOptionListener> optionListeners = new ArrayList<>();
    public HashMap<String, Object> options = new HashMap<>();

    public void addOptionListener(ComboBoxOptionListener l) {
        optionListeners.add(l);
    }

    public JComboBoxWithOptions() {
        super();
        addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                for (String key : options.keySet()) {
                    if (options.get(key) == e.getItem()) {
                        for (ComboBoxOptionListener listener : optionListeners) {
                            listener.optionSelected(options.get(key), key);
                        }
                    }
                }
            }
        });
    }

}
