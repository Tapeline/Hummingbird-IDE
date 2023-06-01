package me.tapeline.carousellib.configuration;

import me.tapeline.carousellib.configuration.exceptions.ConfigurationCorruptedException;
import me.tapeline.carousellib.configuration.exceptions.SectionCorruptedException;
import me.tapeline.carousellib.exceptions.FileReadException;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YamlConfiguration extends ConfigurationFile {

    public YamlConfiguration(File file) {
        super(file);
    }

    @Override
    public void load(ConfigurationSection[] sections, String encoding) throws
            FileReadException, ConfigurationCorruptedException,
            SectionCorruptedException {
        try {
            String strContents = FileUtils.readFileToString(file, encoding);
            Yaml yaml = new Yaml();
            Object obj = yaml.load(strContents);
            if (!(obj instanceof Map<?, ?>))
                throw new ConfigurationCorruptedException(file);
            Map<?, ?> config = (Map<?, ?>) obj;
            for (ConfigurationSection section : sections) {
                if (!config.containsKey(section.getName()))
                    throw new ConfigurationCorruptedException(file);
                section.load(config.get(section.getName()));
            }
        } catch (IOException exception) {
            throw new FileReadException(file, exception);
        }
    }

    @Override
    public void save(ConfigurationSection[] sections, String encoding) throws FileReadException {
        Map<String, Object> config = new HashMap<>();
        for (ConfigurationSection section : sections)
            config.put(section.getName(), section.save());
        Yaml yaml = new Yaml();
        String strContents = yaml.dump(config);
        try {
            FileUtils.writeStringToFile(file, strContents, encoding);
        } catch (IOException exception) {
            throw new FileReadException(file, exception);
        }
    }

}
