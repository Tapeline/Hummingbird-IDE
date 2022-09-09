package me.tapeline.hummingbird.windows.dialog.wizards.settings;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.configuration.Config;
import me.tapeline.hummingbird.configuration.Configuration;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SettingsTab extends JPanel {

    public Map<String, Object> settings = new HashMap<>();
    public Map<String, Component> inputs = new HashMap<>();
    public Map<String, Object> values = new HashMap<>();
    public String section;

    public Object getInput(String key) {
        Component c = inputs.get(key);
        if (c == null) return null;
        if (c instanceof JTextField)
            return ((JTextField) c).getText();
        if (c instanceof JSpinner)
            return ((JSpinner) c).getValue();
        if (c instanceof JCheckBox)
            return values.get(key);
        return null;
    }

    public SettingsTab(String section, Configuration cfg) {
        super();
        this.section = section;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        /*GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;*/
        setAlignmentX(LEFT_ALIGNMENT);
        setAlignmentY(TOP_ALIGNMENT);

        try {
            Field[] fields = Main.cfg.getClass().getFields();
            for (Field field : fields) {
                Config configAnnotation = field.getAnnotation(Config.class);
                if (configAnnotation != null) {
                    if (configAnnotation.section().equals(section) &&
                        configAnnotation.showInSettings()) {
                        String id = !configAnnotation.configurationField().equals("") ?
                                configAnnotation.configurationField() :
                                field.getName();
                        settings.put(
                            id,
                            field.get(Main.cfg)
                        );
                        Component input = null;
                        if (field.getType().equals(String.class)) {
                            input = new JTextField();
                            ((JTextField) input).setText(field.get(Main.cfg).toString());
                        }
                        if (field.getType().equals(Integer.class) ||
                                field.getType().equals(int.class)) {
                            input = new JSpinner();
                            ((JSpinner) input).setValue(field.get(Main.cfg));
                        }
                        if (field.getType().equals(Boolean.class) ||
                                field.getType().equals(boolean.class)) {
                            input = new JCheckBox("", ((Boolean) field.get(Main.cfg)));
                            ((JCheckBox) input).addItemListener(e -> {
                                if (e.getStateChange() == ItemEvent.SELECTED)
                                    values.put(id, true);
                                else
                                    values.put(id, false);
                            });
                        }
                        if (input != null) inputs.put(id, input);
                    }
                }
            }
        } catch (IllegalAccessException e) { return; }

        int line = 0;
        for (String key : inputs.keySet()) {
            JPanel panel = new JPanel();
            panel.setAlignmentX(LEFT_ALIGNMENT);
            panel.setAlignmentY(TOP_ALIGNMENT);
            panel.add(new JLabel(Utils.idToHumanReadableName(key)));
            panel.add(inputs.get(key));
            add(panel);
            line++;
        }
    }

}
