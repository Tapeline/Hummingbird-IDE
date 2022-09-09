package me.tapeline.hummingbird.configuration;

import me.tapeline.hummingbird.expansions.runcfg.RunConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configuration {

    // GENERAL
    @Config
    public String pluginFolder = "plugins";
    @Config
    public String projectFolder = "projects";


    // DEBUG OPTIONS
    @Config(section = "debugOptions")
    public boolean allowDebug = false;


    // APPEARANCE
    @Config(section = "appearance")
    public String theme = "Darcula";
    @Config(section = "appearance")
    public String fontName = "Helvetica";
    @Config(section = "appearance")
    public int fontSize = 12;

    // HIDDEN
    @Config(showInSettings = false)
    public List<String> lastOpened = new ArrayList<>();

    @Config(showInSettings = false)
    public List<RunConfiguration> runConfigurations = new ArrayList<>();


}
