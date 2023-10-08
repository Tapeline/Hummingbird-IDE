package me.tapeline.hummingbird.ide.expansion.files;

import me.tapeline.hummingbird.ide.expansion.RegistryEntry;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.project.Project;

import javax.swing.*;
import java.io.File;
import java.util.List;

public abstract class AbstractContextMenuExpansion implements RegistryEntry {

    public abstract List<Class<?>> getApplicableTypes();
    public abstract void setupContextMenu(AbstractFileType fileType,
                                          EditorWindow editor, JPopupMenu menu,
                                          File contextFile, Project contextProject);

}
