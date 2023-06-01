package me.tapeline.carousellib.configuration.fields;

public class ConfigurationField<T> {

    protected T defaultValue;
    protected T value;

    public ConfigurationField(T value) {
        this.value = value;
        this.defaultValue = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public void load(Object obj) {
        this.value = (T) obj;
    }

    public Object save() {
        return value;
    }

}
