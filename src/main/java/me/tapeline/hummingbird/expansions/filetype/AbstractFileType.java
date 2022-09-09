package me.tapeline.hummingbird.expansions.filetype;

import me.tapeline.hummingbird.expansions.Orderable;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.windows.forms.editor.EditorFrame;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileType implements Orderable {

    public List<String> extensions = new ArrayList<>();
    public BufferedImage icon;

    public AbstractFileType(String... ext) {
        extensions.addAll(Arrays.asList(ext));
        icon = Icons.file;
    }

    public AbstractFileType(BufferedImage icon, String... ext) {
        extensions.addAll(Arrays.asList(ext));
        this.icon = icon;
    }

    public abstract boolean appliesToFile(File file);
    public abstract void setupContextActions(JMenu menu, File contextFile, Project contextProject);
    public abstract void setupContextActions(JPopupMenu menu, File contextFile, Project contextProject);

    public boolean canOpen(File file) {
        return true;
    }

    public boolean doCustomOpen(File file) { return false; }

    public void customOpen(EditorFrame frame, JTree fileTree, File file) {}

    public abstract String id();

}
