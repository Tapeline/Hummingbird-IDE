package me.tapeline.hummingbird.ide.expansion.runconfigs;

import java.util.HashMap;

public class RunConfiguration implements Cloneable {

    private String name;
    private HashMap<String, Object> fields;
    private String runner;

    /**
     * Private constructor left for YAML serialization
     */
    private RunConfiguration() {}

    public RunConfiguration(String name, HashMap<String, Object> fields, String runner) {
        this.name = name;
        this.fields = fields;
        this.runner = runner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Object> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Object> fields) {
        this.fields = fields;
    }

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner;
    }

    @Override
    public RunConfiguration clone() {
        return new RunConfiguration(name, new HashMap<>(fields), runner);
    }

    @Override
    public String toString() {
        return "RunConfiguration{" +
                "name='" + name + '\'' +
                ", runner='" + runner + '\'' +
                '}';
    }

}
