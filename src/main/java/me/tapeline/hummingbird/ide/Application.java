package me.tapeline.hummingbird.ide;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.carousellib.configuration.exceptions.ConfigurationCorruptedException;
import me.tapeline.carousellib.configuration.exceptions.SectionCorruptedException;
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
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Application {

    public static Application instance;

    private final List<AppWindow> windows = new ArrayList<>();
    private final String ideRootPath;
    private final Configuration configuration;
    private final Logger logger;

    public Application() throws ConfigurationCorruptedException,
            FileReadException, SectionCorruptedException {
        logger = Logger.getLogger("Application");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy_HH-mm-ss");
        try {
            new File("logs").mkdirs();
            FileHandler fileHandler = new FileHandler("logs/Log " +
                    format.format(Calendar.getInstance().getTime()) + ".log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        logger.info("Loading fonts");
    }

    public void loadCore() {
        logger.info("Loading core");
        Registry.register(new GenericFile());
        Registry.register(new GenericFolder());
        Registry.register(new ProjectFileType());

        Registry.register(new DarculaTheme());
    }

    public void run() throws Exception {
        logger.info("Starting");
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

        logger.info("Applying theme");
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

        logger.info("Startup complete");
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

    public Logger getLogger() {
        return logger;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
