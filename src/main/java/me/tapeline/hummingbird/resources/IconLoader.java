package me.tapeline.hummingbird.resources;

import me.tapeline.hummingbird.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;

public class IconLoader {

    public static Exception loadIcons(String folder) {
        try {
            for (Field field : Icons.class.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) &&
                    field.getType().equals(BufferedImage.class)) {
                    URL url = Main.class.getClassLoader().getResource(
                            folder + "/" + field.getName() + ".png"
                    );
                    if (url == null) return new Exception(folder + "/" + field.getName() +
                            ".png: icon not found");
                    field.set(Icons.class, ImageIO.read(url));
                }
            }
            return null;
        } catch (Exception e) {
            return e;
        }
    }

}
