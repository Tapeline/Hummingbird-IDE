package me.tapeline.hummingbird.ide.frames.runconfigs;

import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.ui.tools.RunConfigurationListRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RunnerChoiceDialog {

    private JPanel panel;
    private JComboBox<AbstractConfigurationRunner> comboBox;

    public AbstractConfigurationRunner show(List<AbstractConfigurationRunner> data) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        comboBox = new JComboBox<>();
        for (AbstractConfigurationRunner runner : data) comboBox.addItem(runner);
        comboBox.setRenderer(new RunConfigurationListRenderer());
        panel.add(comboBox);
        int result = JOptionPane.showConfirmDialog(
                null,
                panel,
                "Select configuration type",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.YES_OPTION)
            return ((AbstractConfigurationRunner) comboBox.getSelectedItem());
        else
            return null;

    }

}
