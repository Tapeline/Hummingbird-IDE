package me.tapeline.hummingbird.ide.expansion.runconfigs.standard.shellscript;

import me.tapeline.carousellib.icons.items.CShellIcon;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.exceptions.InvalidFieldsInEditorProvidedWarning;
import me.tapeline.hummingbird.ide.exceptions.runconfigs.ConfigurationRunException;
import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfigurationEditorPanel;

import javax.swing.*;
import java.util.HashMap;

public class ShellScriptConfigurationRunner extends AbstractConfigurationRunner {

    @Override
    public String id() {
        return "shellScript";
    }

    @Override
    public Icon icon() {
        return new CShellIcon(16);
    }

    @Override
    public String name() {
        return "Shell Script";
    }

    @Override
    public void run(RunConfiguration configuration) throws ConfigurationRunException {
        Application.getStaticLogger().info("Running shell configuration: "
                + configuration.getFields().get("command"));
    }

    @Override
    public RunConfigurationEditorPanel constructConfigurationEditor(RunConfiguration configuration) {
        return new ShellScriptConfigurationEditor(configuration);
    }

    @Override
    public RunConfiguration getBlankConfiguration() {
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("command", "");
        return new RunConfiguration("Untitled", fields, "shellScript");
    }

    @Override
    public RunConfiguration applyEditorChanges(RunConfigurationEditorPanel editor, RunConfiguration targetConfig)
            throws InvalidFieldsInEditorProvidedWarning {
        if (!(editor instanceof ShellScriptConfigurationEditor))
            throw new InvalidFieldsInEditorProvidedWarning("Invalid editor provided");
        targetConfig.getFields().put("command",
                ((ShellScriptConfigurationEditor) editor).getCommandValue());
        targetConfig.setName(editor.getNameValue());
        return targetConfig;
    }

}
