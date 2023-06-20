package me.tapeline.hummingbird.ide.frames.editor.tooltabs;

import com.jediterm.terminal.TerminalColor;
import com.jediterm.terminal.TextStyle;
import com.jediterm.terminal.emulator.ColorPalette;
import com.jediterm.terminal.ui.settings.DefaultSettingsProvider;
import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.hummingbird.ide.Application;

import java.awt.*;

import com.jediterm.core.Color;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class TerminalSettingsProvider extends DefaultSettingsProvider {

    @Override
    public ColorPalette getTerminalColorPalette() {
        Color[] colors = new Color[] {
                new Color(0),
                new Color(13434880),
                new Color(52480),
                new Color(13487360),
                new Color(2003199),
                new Color(13435085),
                new Color(52685),
                new Color(15066597),
                new Color(5000268),
                new Color(16711680),
                new Color(65280),
                new Color(16776960),
                new Color(4620980),
                new Color(16711935),
                new Color(65535),
                new Color(16777215)
        };
        return new ColorPalette() {
            protected @NotNull Color getForegroundByColorIndex(int i) {
                return colors[i];
            }
            protected @NotNull Color getBackgroundByColorIndex(int i) {
                return colors[i];
            }
        };
    }

    @Override
    public Font getTerminalFont() {
        Font font = new Font("Consolas", Font.PLAIN, 16);
        try {
            String family = Application.instance.getConfiguration().editor().getString("font");
            int size = Application.instance.getConfiguration().editor().getInt("fontSize");
            font = new Font(family, Font.PLAIN, size);
        } catch (FieldNotFoundException ignored) {}
        return font;
    }

    @Override
    public float getTerminalFontSize() {
        return getTerminalFont().getSize();
    }

    @Override
    public TextStyle getDefaultStyle() {
        java.awt.Color bg = UIManager.getColor("TextArea.background");
        java.awt.Color fg = UIManager.getColor("TextArea.foreground");
        return new TextStyle(
                new TerminalColor(fg.getRed(), fg.getGreen(), fg.getBlue()),
                new TerminalColor(bg.getRed(), bg.getGreen(), bg.getBlue())
        );
    }

}
