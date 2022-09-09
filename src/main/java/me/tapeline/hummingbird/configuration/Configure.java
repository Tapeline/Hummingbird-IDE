package me.tapeline.hummingbird.configuration;

import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.utils.Utils;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configure {

    public static Object configureYaml(String path, Class confClass) {
        Yaml yaml = new Yaml();
        Map<String, Object> obj = yaml.load(FS.readFile(path, "# config"));

        Object config = null;
        try {
            config = confClass.newInstance();
            if (obj == null) return config;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }

        try {
            Field[] fields = confClass.getDeclaredFields();
            for (Field field : fields) {
                Config configAnnotation = field.getAnnotation(Config.class);
                if (configAnnotation != null) {
                    String prefix = configAnnotation.section();
                    if (obj.containsKey(prefix + "--" + field.getName())) {
                        if (configAnnotation.configurationField().equals(""))
                            field.set(config, obj.get(prefix + "--" + field.getName()));
                        else
                            field.set(config, obj.get(prefix + "--" + configAnnotation.configurationField()));
                    }
                }
            }
            return config;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public static void saveYaml(String path, Object config) {
        Yaml yaml = new Yaml();
        Map<String, Object> obj = yaml.load(FS.readFile(path, "# config"));
        if (obj == null) obj = new HashMap<>();
        try {
            Field[] fields = config.getClass().getFields();
            for (Field field : fields) {
                Config configAnnotation = field.getAnnotation(Config.class);
                if (configAnnotation != null) {
                    String prefix = configAnnotation.section();
                    obj.put(
                        prefix + "--" + (
                            !configAnnotation.configurationField().equals("")?
                                    configAnnotation.configurationField() :
                                    field.getName()
                        ),
                        field.get(config)
                    );
                }
            }
            FS.writeFile(path, yaml.dump(obj));
        } catch (IllegalAccessException e) {
        }
    }

    public static List<String> getSections(Object config) {
        List<String> sections = new ArrayList<>();
        Field[] fields = config.getClass().getFields();
        for (Field field : fields) {
            Config configAnnotation = field.getAnnotation(Config.class);
            if (configAnnotation != null && !Utils.listContains(sections, configAnnotation.section())) {
                String prefix = configAnnotation.section();
                sections.add(prefix);
            }
        }
        return sections;
    }

}
