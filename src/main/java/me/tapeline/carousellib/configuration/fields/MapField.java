package me.tapeline.carousellib.configuration.fields;

import java.util.List;
import java.util.Map;

public class MapField<K, V> extends ConfigurationField<Map<K, V>> {

    public MapField(Map<K, V> value) {
        super(value);
    }

}
