package me.tapeline.hummingbird.ide.expansion.project;

public class ProjectCreationResult {

    protected String message;
    protected boolean succeeded;

    public ProjectCreationResult(String message, boolean succeeded) {
        this.message = message;
        this.succeeded = succeeded;
    }

    public ProjectCreationResult(String message) {
        this(message, false);
    }

    public ProjectCreationResult(boolean succeeded) {
        this("Success", succeeded);
    }

    public String getMessage() {
        return message;
    }

    public boolean succeeded() {
        return succeeded;
    }

    public static ProjectCreationResult success() {
        return new ProjectCreationResult(true);
    }

    public static ProjectCreationResult fail(String message) {
        return new ProjectCreationResult(message);
    }

}
