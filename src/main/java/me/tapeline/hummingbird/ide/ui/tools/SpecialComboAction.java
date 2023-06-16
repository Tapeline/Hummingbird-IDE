package me.tapeline.hummingbird.ide.ui.tools;

public class SpecialComboAction {

    private String value;
    private String command;

    public SpecialComboAction(String value, String command) {
        this.value = value;
        this.command = command;
    }

    public String getValue() {
        return value;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return value;
    }

}
