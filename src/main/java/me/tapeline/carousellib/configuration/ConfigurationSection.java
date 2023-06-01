package me.tapeline.carousellib.configuration;

import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.carousellib.configuration.exceptions.SectionCorruptedException;
import me.tapeline.carousellib.configuration.fields.ConfigurationField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationSection {

    private HashMap<String, ConfigurationField<?>> fields = new HashMap<>();
    private final String name;

    public ConfigurationSection(String name, HashMap<String, ConfigurationField<?>> fields) {
        this.name = name;
        this.fields = fields;
    }

    public void set(String key, Object value) throws FieldNotFoundException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        fields.get(key).load(value);
    }

    public Object get(String key) throws FieldNotFoundException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        return fields.get(key).get();
    }

    public Integer getInt(String key) throws FieldNotFoundException, ClassCastException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        return (Integer) fields.get(key).get();
    }

    public Boolean getBool(String key) throws FieldNotFoundException, ClassCastException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        return (Boolean) fields.get(key).get();
    }

    public Double getDouble(String key) throws FieldNotFoundException, ClassCastException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        return (Double) fields.get(key).get();
    }

    public List<?> getList(String key) throws FieldNotFoundException, ClassCastException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        return (List<?>) fields.get(key).get();
    }

    public Long getLong(String key) throws FieldNotFoundException, ClassCastException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        return (Long) fields.get(key).get();
    }

    public Map<?, ?> getMap(String key) throws FieldNotFoundException, ClassCastException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        return (Map<?, ?>) fields.get(key).get();
    }

    public String getString(String key) throws FieldNotFoundException, ClassCastException {
        if (!fields.containsKey(key))
            throw new FieldNotFoundException(this, key);
        return (String) fields.get(key).get();
    }

    public void load(Object data) throws SectionCorruptedException {
        if (!(data instanceof Map<?, ?>))
            throw new SectionCorruptedException(this);
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) data).entrySet()) {
            if (!(entry.getKey() instanceof String))
                throw new SectionCorruptedException(this);
            fields.get(entry.getKey()).load(entry.getValue());
        }
    }

    public Object save() {
        Map<String, Object> section = new HashMap<>();
        for (Map.Entry<String, ConfigurationField<?>> entry : fields.entrySet())
            section.put(entry.getKey(), entry.getValue().save());
        return section;
    }

    public String getName() {
        return name;
    }

    public static ConfigurationSection findSection(ConfigurationSection[] array, String name) {
        for (ConfigurationSection section : array)
            if (section.name.equals(name))
                return section;
        return null;
    }

}
