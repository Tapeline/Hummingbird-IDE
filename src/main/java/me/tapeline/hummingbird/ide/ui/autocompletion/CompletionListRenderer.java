package me.tapeline.hummingbird.ide.ui.autocompletion;

import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.expansion.syntax.CompletionSuggestion;
import org.eclipse.jgit.lib.Ref;

import javax.swing.*;
import java.awt.*;

public class CompletionListRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);

        Font font = new Font("Consolas", Font.PLAIN, 16);
        try {
            String family = Application.instance.getConfiguration().editor().getString("font");
            int size = Application.instance.getConfiguration().editor().getInt("fontSize");
            font = new Font(family, Font.PLAIN, size);
        } catch (FieldNotFoundException ignored) {}
        label.setFont(font);

        if (value instanceof CompletionSuggestion) {
            label.setText(((CompletionSuggestion) value).getText() + "   "
                    + ((CompletionSuggestion) value).getInlineDescription());
            label.setIcon(((CompletionSuggestion) value).getIcon());
        }

        return label;
    }
}