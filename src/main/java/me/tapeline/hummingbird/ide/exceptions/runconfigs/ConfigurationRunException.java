package me.tapeline.hummingbird.ide.exceptions.runconfigs;

import me.tapeline.hummingbird.ide.exceptions.HummingbirdException;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;

public class ConfigurationRunException extends HummingbirdException {

    private final RunConfiguration configuration;

    public ConfigurationRunException(String message, RunConfiguration configuration) {
        super(message);
        this.configuration = configuration;
    }

    public ConfigurationRunException(Throwable cause, RunConfiguration configuration) {
        super(cause);
        this.configuration = configuration;
    }

    public RunConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public String toString() {
        return "ConfigurationRunException{" +
                "configuration=" + configuration +
                '}';
    }

}
