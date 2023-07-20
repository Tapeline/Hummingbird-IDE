package me.tapeline.hummingbird.ide.ui.tools;

import me.tapeline.hummingbird.ide.expansion.project.AbstractProjectGenerator;
import org.eclipse.jgit.lib.Ref;

import javax.swing.*;
import java.awt.*;

public class RefListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        if (value instanceof Ref ref) {
            label.setText(ref.getName());
        }

        return label;
    }
}