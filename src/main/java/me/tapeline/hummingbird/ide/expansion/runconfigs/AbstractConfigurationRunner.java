package me.tapeline.hummingbird.ide.expansion.runconfigs;

import me.tapeline.hummingbird.ide.exceptions.InvalidFieldsInEditorProvidedWarning;
import me.tapeline.hummingbird.ide.exceptions.runconfigs.ConfigurationRunException;
import me.tapeline.hummingbird.ide.expansion.RegistryEntry;

import javax.swing.*;

public abstract class AbstractConfigurationRunner implements RegistryEntry {

    public abstract Icon icon();
    public abstract String name();
    public abstract TerminalConfiguration run(RunConfiguration configuration) throws ConfigurationRunException;

    public abstract RunConfigurationEditorPanel constructConfigurationEditor(RunConfiguration configuration);
    public abstract RunConfiguration getBlankConfiguration();
    public abstract RunConfiguration applyEditorChanges(RunConfigurationEditorPanel editor, RunConfiguration targetConfig) throws
            InvalidFieldsInEditorProvidedWarning;

}
