package me.tapeline.hummingbird.ide.ui.tools;

import me.tapeline.hummingbird.ide.Registry;
import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;

import javax.swing.*;
import java.awt.*;

public class RunConfigurationListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        if (value instanceof RunConfiguration configuration) {
            AbstractConfigurationRunner runner =
                    Registry.getConfigurationRunner(configuration.getRunner());
            if (runner != null)
                label.setIcon(runner.icon());
            label.setText(configuration.getName());
        }

        if (value instanceof AbstractConfigurationRunner runner) {
            label.setIcon(runner.icon());
            label.setText(runner.name());
        }

        return label;
    }
}