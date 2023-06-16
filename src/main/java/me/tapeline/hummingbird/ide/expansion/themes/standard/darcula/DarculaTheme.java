package me.tapeline.hummingbird.ide.expansion.themes.standard.darcula;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.hummingbird.ide.expansion.themes.AbstractTheme;

import javax.swing.*;
import java.awt.*;

public class DarculaTheme extends AbstractTheme {

    @Override
    public boolean isDark() {
        return true;
    }

    @Override
    public String id() {
        return "dark";
    }

    @Override
    public void onApply() {
        FlatDarculaLaf.setup();
        UIManager.put("TextArea.background", new Color(0x2B2B2C));
    }

}
