package me.tapeline.hummingbird.ide.expansion.project;

import me.tapeline.hummingbird.ide.expansion.runconfigs.RunConfiguration;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class ProjectGeneratorParamPanel extends JPanel {

    public ProjectGeneratorParamPanel() {
        super();
        setLayout(new BorderLayout());
    }

    public abstract String name();

    public abstract HashMap<String, Object> getConfiguredValues();

}
