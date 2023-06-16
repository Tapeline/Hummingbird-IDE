package me.tapeline.hummingbird.ide.expansion.runconfigs;

import javax.swing.*;
import java.awt.*;

public class RunConfigurationEditorPanel extends JPanel {

    protected RunConfiguration configuration;

    protected JLabel nameFieldHeader;
    protected JTextField nameFieldWidget;
    protected JPanel nameFieldPanel;

    public RunConfigurationEditorPanel(RunConfiguration configuration) {
        super();
        this.configuration = configuration;

        setLayout(new BorderLayout());
        nameFieldPanel = new JPanel();
        nameFieldPanel.setLayout(new BorderLayout());
        nameFieldHeader = new JLabel("Name");
        nameFieldWidget = new JTextField(configuration.getName());
        nameFieldPanel.add(nameFieldHeader, BorderLayout.LINE_START);
        nameFieldPanel.add(nameFieldWidget, BorderLayout.CENTER);
        add(nameFieldPanel, BorderLayout.PAGE_START);
    }

    public String getNameValue() {
        return nameFieldWidget.getText();
    }

    public RunConfiguration getConfiguration() {
        return configuration;
    }
    
}
