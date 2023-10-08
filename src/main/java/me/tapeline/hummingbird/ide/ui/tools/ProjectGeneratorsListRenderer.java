package me.tapeline.hummingbird.ide.ui.tools;

import me.tapeline.hummingbird.ide.Registry;
import me.tapeline.hummingbird.ide.expansion.project.AbstractProjectGenerator;
import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;

import javax.swing.*;
import java.awt.*;

public class ProjectGeneratorsListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        if (value instanceof AbstractProjectGenerator generator) {
            label.setText(generator.name());
            label.setIcon(generator.icon());
        }

        return label;
    }
}