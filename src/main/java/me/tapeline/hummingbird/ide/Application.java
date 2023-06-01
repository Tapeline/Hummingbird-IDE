package me.tapeline.hummingbird.ide;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import me.tapeline.carousellib.configuration.exceptions.ConfigurationCorruptedException;
import me.tapeline.carousellib.configuration.exceptions.SectionCorruptedException;
import me.tapeline.carousellib.data.Pair;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.exceptions.FileReadException;
import me.tapeline.carousellib.icons.CBundledIcon;
import me.tapeline.hummingbird.ide.configuration.Configuration;
import me.tapeline.hummingbird.ide.expansion.files.GenericFile;
import me.tapeline.hummingbird.ide.expansion.files.GenericFolder;
import me.tapeline.hummingbird.ide.expansion.files.ProjectFileType;
import me.tapeline.hummingbird.ide.expansion.themes.AbstractTheme;
import me.tapeline.hummingbird.ide.expansion.themes.builtin.darcula.DarculaTheme;
import me.tapeline.hummingbird.ide.frames.AppWindow;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.frames.splash.SplashScreen;
import me.tapeline.hummingbird.ide.frames.welcome.WelcomeWindow;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Application {

    public static Application instance;

    private final List<AppWindow> windows = new ArrayList<>();
    private final String ideRootPath;
    private final Configuration configuration;

    public Application() throws ConfigurationCorruptedException,
            FileReadException, SectionCorruptedException {
        ideRootPath = new File("").getAbsolutePath();
        configuration = new Configuration(Paths.get(
                ideRootPath, "config.yml"
        ).toFile());
        instance = this;
    }

    public static void execute(String[] args) {
        try {
            Application app = new Application();
            app.run();
        } catch (Exception e) {
            Dialogs.exception("Exception", "An error occurred", e);
        }
    }

    public void loadFonts() {

    }

    public void loadCore() {
        Registry.register(new GenericFile());
        Registry.register(new GenericFolder());
        Registry.register(new ProjectFileType());

        Registry.register(new DarculaTheme());
    }

    public void run() throws Exception {
        SplashScreen splashScreen = new SplashScreen(ImageIO.read(
                Application.class.getClassLoader().getResource(
                        "images/splash.png"
                )
        ));
        splashScreen.setVisible(true);
        Locale.setDefault(Locale.ENGLISH);
        FlatDarculaLaf.setup();

        loadFonts();
        loadCore();

        AbstractTheme theme = Registry.getTheme(configuration.appearance().getString("theme"));
        if (theme == null) {
            Dialogs.warn(splashScreen, "Theme not found", "Theme provided in " +
                    "config does not exist. Defaulting.");
            theme = new DarculaTheme();
        }
        theme.onApply();
        CBundledIcon.darkMode = theme.isDark();

        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
        splashScreen.setVisible(false);
        splashScreen.dispose();

        //WelcomeWindow welcomeWindow = new WelcomeWindow();
        EditorWindow window = new EditorWindow(null);
    }

    public void addWindow(AppWindow window) {
        windows.add(window);
    }

    public void removeWindow(AppWindow window) {
        windows.remove(window);
    }

    public List<AppWindow> getWindows() {
        return windows;
    }

}
