package me.tapeline.hummingbird.expansions.runcfg;

import me.tapeline.hummingbird.utils.Dict;

import java.util.Map;

public class RunConfiguration {

    public String command;
    public String name;

    public RunConfiguration() {}

    public RunConfiguration(String name, String command) {
        this.command = command;
        this.name = name;
    }

    public Map<String, Object> toMap() {
        return Dict.map(
                Dict.pair("name", name),
                Dict.pair("cmd", command)
        );
    }

    public String toString() {
        return name;
    }

    public void fromMap(Map<String, Object> obj) {
        if (obj.containsKey("name") && obj.containsKey("cmd")) {
            command = (String) obj.get("cmd");
            name = (String) obj.get("name");
        }
    }

    public static RunConfiguration staticFromMap(Map<String, Object> obj) {
        if (obj.containsKey("name") && obj.containsKey("cmd")) {
            RunConfiguration n = new RunConfiguration("", "");
            n.fromMap(obj);
            return n;
        }
        return null;
    }
}
