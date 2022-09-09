package me.tapeline.hummingbird;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import me.tapeline.hummingbird.configuration.Configuration;
import me.tapeline.hummingbird.configuration.Configure;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.filetype.GeneralFile;
import me.tapeline.hummingbird.expansions.filetype.GeneralFolder;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.highlighter.Highlight;
import me.tapeline.hummingbird.expansions.themes.DarculaTheme;
import me.tapeline.hummingbird.expansions.themes.LightTheme;
import me.tapeline.hummingbird.resources.FontLoader;
import me.tapeline.hummingbird.resources.IconLoader;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.JFileTree;
import me.tapeline.hummingbird.ui.jcodeeditor.HighlightCooldown;
import me.tapeline.hummingbird.utils.ConfigSaveThread;
import me.tapeline.hummingbird.windows.HFrame;
import me.tapeline.hummingbird.windows.dialog.wizards.settings.SettingsDialog;
import me.tapeline.hummingbird.windows.forms.error.ErrorDialog;
import me.tapeline.hummingbird.windows.forms.welcome.WelcomeScreen;
import me.tapeline.hummingbirdplugins.quail.QuailPlugin;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import me.tapeline.hummingbird.windows.forms.splash.SplashScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Main {

    public static String versionType = "dev-preview";
    public static int majorVersion = 0;
    public static int minorVersion = 1;
    public static int patchVersion = 0;
    public static String snapshot = "22-35-THU";

    public static String version = "v." + majorVersion + "." + minorVersion + "."
            + patchVersion + "-" + versionType;
    public static String fullVersion = "v." + majorVersion + "." + minorVersion + "."
            + patchVersion + "-" + versionType
            + " SNAPSHOT-" + snapshot;


    public static List<HFrame> openedFrames = new ArrayList<>();
    public static String OS = null;
    public static Configuration cfg;
    public static String configPath = "config.yml";
    public static String getOsName() {
        if(OS == null) { OS = System.getProperty("os.name"); }
        return OS;
    }

    public static HighlightCooldown highlightCooldown = new HighlightCooldown();

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public static boolean isUnix() {
        return getOsName().startsWith("Linux");
    }

    public static void reloadConfig() {
        for (HFrame frame : openedFrames) {
            frame.reloadFromConfig();
        }
    }

    public static void load() throws Exception {
        String iconsFolder = "images";
        String fontsFolder = "fonts";

        BufferedImage splashImage = ImageIO.read(Main.class.getClassLoader().getResource(
                iconsFolder + "/splash.png"
        ));
        final SplashScreen splashScreen = new SplashScreen(640, 365, splashImage);
        splashScreen.setVisible(true);

        // Load Locale
        Locale.setDefault(Locale.ENGLISH);

        // Apply default theme
        FlatDarculaLaf.setup();

        // Load config
        cfg = (Configuration) Configure.configureYaml(configPath, Configuration.class);

        // Load icons
        Exception iconException = IconLoader.loadIcons(iconsFolder);
        if (iconException != null)
            throw iconException;

        // Load font
        Exception fontException = FontLoader.loadFonts(fontsFolder);
        if (fontException != null)
            throw fontException;

        // Load defaults to registry
        Registry.fileTypes.add(new GeneralFolder());
        Registry.fileTypes.add(new GeneralFile());

        Registry.themes.add(new DarculaTheme());
        Registry.themes.add(new LightTheme());

        Registry.registerPlugin(new QuailPlugin());

        Registry.order();

        // Apply user theme
        Registry.applyTheme(Registry.getCurrentTheme());

        // Stay for a while
        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
        splashScreen.setVisible(false);
        splashScreen.dispose();

        WelcomeScreen welcomeScreen = new WelcomeScreen(cfg.lastOpened);
        //JFileTree.main(null);
    }

    public static void save() {
        Configure.saveYaml(configPath, cfg);
    }

    public static void main(String[] args) {
        try {
            load();
            save();
        } catch (Exception e) {
            ErrorDialog dialog = new ErrorDialog(e);
            System.exit(0);
        }

    }

    public static void saveCfg() {
        Configure.saveYaml("config.yml", cfg);
    }
}