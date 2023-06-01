package me.tapeline.hummingbird.ide.exceptions;

import java.io.File;

public class ProjectDirectoryException extends HummingbirdException {

    public File project;

    public ProjectDirectoryException(String message, File project) {
        super(message);
        this.project = project;
    }

    public ProjectDirectoryException(Throwable cause, File project) {
        super(cause);
        this.project = project;
    }

    @Override
    public String toString() {
        return "ProjectDirectoryException{" +
                "project=" + project +
                "} " + getMessage();
    }

}
