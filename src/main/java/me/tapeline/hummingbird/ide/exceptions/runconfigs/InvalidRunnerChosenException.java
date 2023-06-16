package me.tapeline.hummingbird.ide.exceptions.runconfigs;

import me.tapeline.hummingbird.ide.expansion.runconfigs.AbstractConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;

public class InvalidRunnerChosenException extends ConfigurationRunException {

    private final AbstractConfigurationRunner runner;

    public InvalidRunnerChosenException(String message, RunConfiguration configuration,
                                        AbstractConfigurationRunner runner) {
        super(message, configuration);
        this.runner = runner;
    }

    public InvalidRunnerChosenException(Throwable cause, RunConfiguration configuration,
                                        AbstractConfigurationRunner runner) {
        super(cause, configuration);
        this.runner = runner;
    }

    public AbstractConfigurationRunner getRunner() {
        return runner;
    }

    @Override
    public String toString() {
        return "InvalidRunnerChosenException{" +
                "configuration=" + getConfiguration() + ", " +
                "runner=" + runner +
                '}';
    }

}
