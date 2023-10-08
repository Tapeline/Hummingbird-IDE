package me.tapeline.hummingbird.ide.expansion.project.standard.empty;

import me.tapeline.hummingbird.ide.expansion.files.standard.ProjectFileType;
import me.tapeline.hummingbird.ide.expansion.project.AbstractProjectGenerator;
import me.tapeline.hummingbird.ide.expansion.project.ProjectCreationResult;
import me.tapeline.hummingbird.ide.expansion.project.ProjectGeneratorParamPanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;

public class EmptyProjectGenerator extends AbstractProjectGenerator {

    @Override
    public String id() {
        return "empty";
    }

    @Override
    public Icon icon() {
        return ProjectFileType.getIcon();
    }

    @Override
    public String name() {
        return "Empty project";
    }

    @Override
    public @Nullable ProjectGeneratorParamPanel[] getParamPanels() {
        return null;
    }

    @Override
    public ProjectCreationResult createProject(String projectName,
                                               File projectRoot,
                                               HashMap<String, Object> configuredValues) {
        return ProjectCreationResult.success();
    }

}
