package me.tapeline.carousellib.exceptions;

import java.io.File;
import java.io.IOException;

public class FileReadException extends CarouselException {

    private final File targetFile;
    private final IOException mentionedException;

    public FileReadException(File targetFile, IOException mentionedException) {
        super("File " + targetFile + ": " + mentionedException);
        this.targetFile = targetFile;
        this.mentionedException = mentionedException;
    }

    public File getTargetFile() {
        return targetFile;
    }

    public IOException getMentionedException() {
        return mentionedException;
    }

}
