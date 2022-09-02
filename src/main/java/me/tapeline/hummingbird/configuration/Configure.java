package me.tapeline.hummingbird.configuration;

import me.tapeline.hummingbird.filesystem.FS;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

public class Configuration {

    public static Object configureYaml(String path, Class confClass) {
        Yaml yaml = new Yaml();
        Map<String, Object> obj = yaml.load(FS.readFile(path, "# config"));

        Object config = null;
        try {
            config = confClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }

        try {
            Field[] fields = confClass.getDeclaredFields();
            for (Field field : fields) {
                Config configAnnotation = field.getAnnotation(Config.class);
                if (configAnnotation != null) {
                    if (obj.containsKey(field.getName())) {
                        if (configAnnotation.configurationField() == null)
                            field.set(config, obj.get(field.getName()));
                        else
                            field.set(config, obj.get(configAnnotation.configurationField()));
                    }
                }
            }
            return config;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

}
