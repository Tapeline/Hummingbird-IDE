package me.tapeline.hummingbird.ide.expansion.project;

import me.tapeline.hummingbird.ide.expansion.RegistryEntry;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;

public abstract class AbstractProjectGenerator implements RegistryEntry {

    public abstract Icon icon();
    public abstract String name();
    public abstract @Nullable ProjectGeneratorParamPanel[] getParamPanels();
    public abstract ProjectCreationResult createProject(String projectName,
                                                        File projectRoot,
                                                        HashMap<String, Object> configuredValues);

}
