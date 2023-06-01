package me.tapeline.carousellib.configuration.exceptions;

import me.tapeline.carousellib.exceptions.CarouselException;

import java.io.File;

public class ConfigurationCorruptedException extends CarouselException {

    private final File targetFile;

    public ConfigurationCorruptedException(File targetFile) {
        super(targetFile.toString());
        this.targetFile = targetFile;
    }

    public File getTargetFile() {
        return targetFile;
    }

}
