package me.tapeline.hummingbird.ide;

import me.tapeline.hummingbird.ide.expansion.RegistryEntry;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.expansion.project.AbstractProjectGenerator;
import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.themes.AbstractTheme;

import java.util.ArrayList;
import java.util.List;

public class Registry {

    public static List<AbstractTheme> themes = new ArrayList<>();
    public static List<AbstractFileType> fileTypes = new ArrayList<>();
    public static List<AbstractConfigurationRunner> configurationRunners = new ArrayList<>();
    public static List<AbstractProjectGenerator> projectGenerators = new ArrayList<>();

    public static AbstractTheme currentTheme = null;

    public static void registerAll(List<? extends RegistryEntry> objects) {
        for (RegistryEntry object : objects)
            register(object);
    }

    public static void register(RegistryEntry object) {
        if (object instanceof AbstractFileType fileType)
            fileTypes.add(0, fileType);
        else if (object instanceof AbstractTheme theme)
            themes.add(0, theme);
        else if (object instanceof AbstractConfigurationRunner runner)
            configurationRunners.add(0, runner);
        else if (object instanceof AbstractProjectGenerator generator)
            projectGenerators.add(generator);
    }

    public static AbstractTheme getTheme(String id) {
        for (AbstractTheme theme : themes)
            if (theme.id().equals(id))
                return theme;
        return null;
    }

    public static AbstractFileType getFileType(String id) {
        for (AbstractFileType fileType : fileTypes)
            if (fileType.id().equals(id))
                return fileType;
        return null;
    }

    public static AbstractConfigurationRunner getConfigurationRunner(String id) {
        for (AbstractConfigurationRunner runner : configurationRunners)
            if (runner.id().equals(id))
                return runner;
        return null;
    }

    public static AbstractProjectGenerator getProjectGenerator(String id) {
        for (AbstractProjectGenerator generator : projectGenerators)
            if (generator.id().equals(id))
                return generator;
        return null;
    }

}
