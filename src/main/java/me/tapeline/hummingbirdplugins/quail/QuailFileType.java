package me.tapeline.hummingbirdplugins.quail;

import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.filetype.GeneralFile;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.utils.Utils;

import javax.swing.*;
import java.io.File;

public class QuailFileType extends AbstractFileType {
    @Override
    public int weight() {
        return 1000;
    }

    @Override
    public boolean appliesToFile(File file) {
        return Utils.getExtension(file).equals("q");
    }

    @Override
    public void setupContextActions(JMenu menu, File contextFile, Project contextProject) {
        new GeneralFile().setupContextActions(menu, contextFile, contextProject);
    }

    @Override
    public void setupContextActions(JPopupMenu menu, File contextFile, Project contextProject) {
        new GeneralFile().setupContextActions(menu, contextFile, contextProject);
    }

    @Override
    public String id() {
        return "q";
    }
}
