package me.tapeline.hummingbird.ide.expansion.syntax;

import me.tapeline.hummingbird.ide.expansion.RegistryEntry;
import me.tapeline.hummingbird.ide.project.Project;

import java.io.File;

public abstract class AbstractSyntaxAdapterType implements RegistryEntry {

    public abstract boolean appliesForProject(Project project);
    public abstract boolean appliesForFile(Project project, File file);
    public abstract AbstractSyntaxAdapter constructAdapter(Project project);

}
