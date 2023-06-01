package me.tapeline.hummingbird.ide.project;

import me.tapeline.hummingbird.ide.configuration.ProjectConfiguration;
import me.tapeline.hummingbird.ide.exceptions.ProjectDirectoryException;

import java.io.File;
import java.nio.file.Paths;

public class Project {

    protected File root;
    protected ProjectConfiguration configuration;

    public Project(File root) throws ProjectDirectoryException {
        this.root = root;
        if (!root.exists())
            if (!constructEmptyProjectAt(root))
                throw new ProjectDirectoryException("Unable to initialize empty project", root);
        if (!Paths.get(root.getAbsolutePath(), ".ide").toFile().exists() ||
            !Paths.get(root.getAbsolutePath(), ".ide", "project.yml").toFile().exists())
            repairProjectAt(root);
        try {
            configuration = new ProjectConfiguration(
                    Paths.get(root.getAbsolutePath(), ".ide", "project.yml").toFile()
            );
            configuration.load();
        } catch (Exception e) { throw new ProjectDirectoryException(e, root); }
    }

    public void save() throws ProjectDirectoryException {
        try {
            configuration.save();
        } catch (Exception e) { throw new ProjectDirectoryException(e, root); }
    }

    public static boolean constructEmptyProjectAt(File projectRoot) throws ProjectDirectoryException {
        boolean mk1 = projectRoot.mkdirs();
        boolean mk2 = Paths.get(projectRoot.getAbsolutePath(), ".ide").toFile().mkdir();
        try {
            ProjectConfiguration defaultConfig = new ProjectConfiguration(
                    Paths.get(projectRoot.getAbsolutePath(), ".ide", "project.yml").toFile()
            );
            defaultConfig.save();
        } catch (Exception e) { throw new ProjectDirectoryException(e, projectRoot); }
        return mk1 && mk2;
    }

    public static void repairProjectAt(File projectRoot) throws ProjectDirectoryException {
        Paths.get(projectRoot.getAbsolutePath(), ".ide").toFile().mkdir();
        try {
            ProjectConfiguration defaultConfig = new ProjectConfiguration(
                    Paths.get(projectRoot.getAbsolutePath(), ".ide", "project.yml").toFile()
            );
            defaultConfig.save();
        } catch (Exception e) { throw new ProjectDirectoryException(e, projectRoot); }
    }

    public File getRoot() {
        return root;
    }

    public ProjectConfiguration getConfiguration() {
        return configuration;
    }

}
