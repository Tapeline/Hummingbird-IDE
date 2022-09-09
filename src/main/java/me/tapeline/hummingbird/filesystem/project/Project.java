package me.tapeline.hummingbird.filesystem.project;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.filesystem.project.fsmodel.FileSystemModel;

import java.io.File;
import java.io.IOException;

public class Project {

    public FileSystemModel fileSystem;
    public File root;

    public Project(File f) {
        root = f;
        fileSystem = new FileSystemModel(root);
        boolean hasProject = false;
        for (String path : Main.cfg.lastOpened) {
            if (path.equals(f.getAbsolutePath())) {
                hasProject = true;
                break;
            }
        }
        if (!hasProject) Main.cfg.lastOpened.add(f.getAbsolutePath());
        Main.saveCfg();
    }

    public void saveProject() throws IOException {
        fileSystem.root.save();
    }

    public void resolveFiles() {
        fileSystem.reload();
    }

    public File getRelativeToProjectRoot(File f) {
        String p = f.getAbsolutePath().replace(root.getAbsolutePath(), "");
        if (p.startsWith("/")) p = p.substring(1);
        p = root.getName() + "/" + p;
        return new File(p);
    }

}
