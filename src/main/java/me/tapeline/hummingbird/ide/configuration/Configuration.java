package me.tapeline.hummingbird.ide.configuration;

import me.tapeline.carousellib.configuration.ConfigurationSection;
import me.tapeline.carousellib.configuration.YamlConfiguration;
import me.tapeline.carousellib.configuration.exceptions.ConfigurationCorruptedException;
import me.tapeline.carousellib.configuration.exceptions.SectionCorruptedException;
import me.tapeline.carousellib.configuration.fields.LongField;
import me.tapeline.carousellib.configuration.fields.StringField;
import me.tapeline.carousellib.data.Dict;
import me.tapeline.carousellib.data.Pair;
import me.tapeline.carousellib.exceptions.FileReadException;

import java.io.File;

public class Configuration {

    private YamlConfiguration configFile;
    private ConfigurationSection[] sections = {
            new ConfigurationSection("latestRun", Dict.make(
                    new Pair<>("timestamp", new LongField(System.currentTimeMillis()))
            )),
            new ConfigurationSection("appearance", Dict.make(
                    new Pair<>("theme", new StringField("dark"))
            ))
    };

    public Configuration(File config) throws FileReadException,
            ConfigurationCorruptedException,
            SectionCorruptedException {
        configFile = new YamlConfiguration(config);
        if (!config.exists())
            configFile.save(sections, GlobalFlags.encoding);
        configFile.load(sections, GlobalFlags.encoding);
    }

    public void load() throws ConfigurationCorruptedException,
            FileReadException, SectionCorruptedException {
        configFile.load(sections, GlobalFlags.encoding);
    }

    public void save() throws FileReadException {
        configFile.save(sections, GlobalFlags.encoding);
    }

    public ConfigurationSection latestRun() {
        return sections[0];
    }

    public ConfigurationSection appearance() {
        return sections[1];
    }

}
