package me.tapeline.hummingbird.resources;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.configuration.Configuration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;

public class FontLoader {

    public static Exception loadFonts(String folder) {
        try {
            for (Field field : Fonts.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) &&
                    field.getType().equals(Font.class)) {
                    InputStream fontStream = Main.class.getResourceAsStream(
                            "/" + folder + "/" + field.getName() + ".ttf"
                    );
                    if (fontStream == null) return new Exception(folder + "/" + field.getName() +
                            ".ttf: font not found");
                    Font f = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                    f = f.deriveFont(Font.PLAIN, Main.cfg.fontSize);
                    field.set(Fonts.class, f);
                }
            }
            return null;
        } catch (Exception e) {
            return e;
        }
    }

}
