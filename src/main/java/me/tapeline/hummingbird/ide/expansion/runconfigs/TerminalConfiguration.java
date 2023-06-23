package me.tapeline.hummingbird.ide.expansion.runconfigs;

import java.util.Arrays;
import java.util.Map;

public class TerminalConfiguration {

    private String[] command;
    private Map<String, String> env;

    public TerminalConfiguration(String[] command, Map<String, String> env) {
        this.command = command;
        this.env = env;
    }

    public String[] getCommand() {
        return command;
    }

    public Map<String, String> getEnv() {
        return env;
    }

    @Override
    public String toString() {
        return "TerminalConfiguration{" +
                "command=" + Arrays.toString(command) +
                ", env=" + env +
                '}';
    }

}
