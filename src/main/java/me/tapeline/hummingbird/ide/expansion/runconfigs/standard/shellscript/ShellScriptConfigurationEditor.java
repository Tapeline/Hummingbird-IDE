package me.tapeline.hummingbird.ide.expansion.runconfigs.standard.shellscript;

import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfigurationEditorPanel;

import javax.swing.*;
import java.awt.*;

public class ShellScriptConfigurationEditor extends RunConfigurationEditorPanel {

    protected JLabel shellCommandFieldHeader;
    protected JTextField shellCommandFieldWidget;
    protected JPanel shellCommandFieldPanel;

    public ShellScriptConfigurationEditor(RunConfiguration configuration) {
        super(configuration);

        shellCommandFieldPanel = new JPanel();
        shellCommandFieldPanel.setLayout(new BorderLayout());
        shellCommandFieldHeader = new JLabel("Command");
        if (configuration.getFields().containsKey("command"))
            shellCommandFieldWidget = new JTextField((String) configuration.getFields().get("command"));
        else
            shellCommandFieldWidget = new JTextField("");
        shellCommandFieldPanel.add(shellCommandFieldHeader, BorderLayout.LINE_START);
        shellCommandFieldPanel.add(shellCommandFieldWidget, BorderLayout.CENTER);
        add(shellCommandFieldPanel, BorderLayout.CENTER);
    }

    public String getCommandValue() {
        return shellCommandFieldWidget.getText();
    }

}
