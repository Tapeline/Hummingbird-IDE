package me.tapeline.carousellib.configuration;

import me.tapeline.carousellib.configuration.exceptions.ConfigurationCorruptedException;
import me.tapeline.carousellib.configuration.exceptions.SectionCorruptedException;
import me.tapeline.carousellib.exceptions.FileReadException;

import java.io.File;

public abstract class ConfigurationFile {

    protected File file;

    public ConfigurationFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public abstract void load(ConfigurationSection[] sections, String encoding) throws
            FileReadException, ConfigurationCorruptedException,
            SectionCorruptedException;

    public abstract void save(ConfigurationSection[] sections, String encoding) throws FileReadException;

}
