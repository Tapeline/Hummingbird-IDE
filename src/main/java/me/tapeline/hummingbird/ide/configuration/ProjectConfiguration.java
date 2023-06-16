package me.tapeline.hummingbird.ide.configuration;

import me.tapeline.carousellib.configuration.ConfigurationSection;
import me.tapeline.carousellib.configuration.YamlConfiguration;
import me.tapeline.carousellib.configuration.exceptions.ConfigurationCorruptedException;
import me.tapeline.carousellib.configuration.exceptions.SectionCorruptedException;
import me.tapeline.carousellib.configuration.fields.ListField;
import me.tapeline.carousellib.configuration.fields.LongField;
import me.tapeline.carousellib.configuration.fields.StringField;
import me.tapeline.carousellib.data.Dict;
import me.tapeline.carousellib.data.Pair;
import me.tapeline.carousellib.exceptions.FileReadException;
import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;

import java.io.File;
import java.util.Collections;

public class ProjectConfiguration {

    private YamlConfiguration configFile;
    private ConfigurationSection[] sections = {
            new ConfigurationSection("general", Dict.make(
                    new Pair<>("type", new StringField("plain"))
            )),
            new ConfigurationSection("running", Dict.make(
                    new Pair<>("configurations",
                            new ListField<RunConfiguration>(Collections.emptyList()))
            ))
    };

    public ProjectConfiguration(File config) throws FileReadException,
            ConfigurationCorruptedException,
            SectionCorruptedException {
        configFile = new YamlConfiguration(config);
        if (!config.exists())
            save();
        load();
    }

    public void load() throws ConfigurationCorruptedException,
            FileReadException, SectionCorruptedException {
        configFile.load(sections, GlobalFlags.encoding);
    }

    public void save() throws FileReadException {
        configFile.save(sections, GlobalFlags.encoding);
    }

    public ConfigurationSection general() {
        return sections[0];
    }
    public ConfigurationSection running() {
        return sections[1];
    }

}
