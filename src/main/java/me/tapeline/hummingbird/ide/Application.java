package me.tapeline.hummingbird.ide;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.carousellib.configuration.exceptions.ConfigurationCorruptedException;
import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.carousellib.configuration.exceptions.SectionCorruptedException;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.carousellib.exceptions.FileReadException;
import me.tapeline.carousellib.icons.CBundledIcon;
import me.tapeline.hummingbird.ide.configuration.Configuration;
import me.tapeline.hummingbird.ide.expansion.files.standard.GenericFile;
import me.tapeline.hummingbird.ide.expansion.files.standard.GenericFolder;
import me.tapeline.hummingbird.ide.expansion.files.standard.ProjectFileType;
import me.tapeline.hummingbird.ide.expansion.plugins.AbstractPlugin;
import me.tapeline.hummingbird.ide.expansion.project.standard.empty.EmptyProjectGenerator;
import me.tapeline.hummingbird.ide.expansion.runconfigs.standard.shellscript.ShellScriptConfigurationRunner;
import me.tapeline.hummingbird.ide.expansion.themes.AbstractTheme;
import me.tapeline.hummingbird.ide.expansion.themes.standard.darcula.DarculaTheme;
import me.tapeline.hummingbird.ide.frames.AppWindow;
import me.tapeline.hummingbird.ide.frames.splash.SplashScreen;
import me.tapeline.hummingbird.ide.frames.welcome.WelcomeWindow;
import org.pf4j.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
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

    public static Logger getStaticLogger() {
        return instance.logger;
    }

    private final List<AppWindow> windows = new ArrayList<>();
    private final String ideRootPath;
    private final Configuration configuration;
    private final Logger logger;
    private PluginManager pluginManager;

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
        try {
            pluginManager = new DefaultPluginManager(
                    new File(configuration.plugins().getString("home")).toPath()
            );
        } catch (FieldNotFoundException e) { e.printStackTrace(); }
        Dialogs.logger = logger;
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

        Registry.register(new ShellScriptConfigurationRunner());

        Registry.register(new EmptyProjectGenerator());
    }

    public void loadPlugins() {
        logger.info("Loading plugins");
        if (pluginManager == null) {
            logger.severe("Plugin manager appears to be null");
            return;
        }
        List<String> enabled = new ArrayList<>();
        try {
            enabled = (List<String>) configuration.plugins().getList("enabled");
        } catch (FieldNotFoundException e) {
            e.printStackTrace();
        }
        Path pluginRoot = pluginManager.getPluginsRoots().get(0);
        for (String enabledPlugin : enabled) {
            logger.info("Trying to load plugin " + enabledPlugin);
            try {
                String id = pluginManager.loadPlugin(
                        Paths.get(pluginRoot.toFile().getAbsolutePath(), enabledPlugin)
                );
                PluginState state = pluginManager.startPlugin(id);
                if (state.equals(PluginState.STARTED)) {
                    logger.info("Started plugin " + id);
                    handlePlugin(pluginManager.getPlugin(id));
                } else
                    logger.warning("Unable to start plugin " + id + " : " + state);
            } catch (Exception e) {
                Dialogs.exception(null, "Error while loading plugin " + enabledPlugin, e);
            }
        }
    }

    private void handlePlugin(PluginWrapper wrapper) {
        logger.info("Handling plugin " + wrapper.getPluginId());
        if (wrapper.getPlugin() == null) {
            logger.warning("Plugin is null. Abort handling");
            return;
        }
        if (!(wrapper.getPlugin() instanceof AbstractPlugin)) {
            logger.warning("Plugin is not an instance of " +
                    "me.tapeline.hummingbird.ide.expansion.plugins.AbstractPlugin. Abort handling");
            return;
        }
        AbstractPlugin plugin = ((AbstractPlugin) wrapper.getPlugin());
        plugin.onLoad(this);
        if (plugin.getFileTypeProvider() != null)
            Registry.registerAll(plugin.getFileTypeProvider().providedFileTypes());
        if (plugin.getSyntaxProvider() != null)
            Registry.registerAll(plugin.getSyntaxProvider().providedAdapters());
        if (plugin.getThemeProvider() != null)
            Registry.registerAll(plugin.getThemeProvider().providedThemes());
        logger.info("Handling successful");
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
        loadPlugins();

        logger.info("Applying theme");
        AbstractTheme theme = Registry.getTheme(configuration.appearance().getString("theme"));
        if (theme == null) {
            Dialogs.warn(splashScreen, "Theme not found", "Theme provided in " +
                    "config does not exist. Defaulting.");
            theme = new DarculaTheme();
        }
        theme.onApply();
        Font font = new Font("SansSerif", Font.PLAIN, 12);
        try {
            String family = Application.instance.getConfiguration().appearance().getString("font");
            int size = Application.instance.getConfiguration().appearance().getInt("fontSize");
            font = new Font(family, Font.PLAIN, size);
        } catch (FieldNotFoundException ignored) {}
        setGlobalUIFont(font);
        CBundledIcon.darkMode = theme.isDark();
        Registry.currentTheme = theme;

        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
        splashScreen.setVisible(false);
        splashScreen.dispose();

        logger.info("Startup complete");
        WelcomeWindow welcomeWindow = new WelcomeWindow();
        //EditorWindow window = new EditorWindow(new Project(new File("src/test/resources/testProj")));
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

    public void saveConfig() {
        try {
            getConfiguration().save();
        } catch (FileReadException e) {
            Dialogs.exception("Error", "Exception during config saving", e);
        }
    }

    public void setGlobalUIFont(Font font) {
        UIManager.put("Button.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("CheckBoxMenuItem.font", font);
        UIManager.put("ColorChooser.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("DesktopIcon.font", font);
        UIManager.put("FormattedTextField.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("List.font", font);
        UIManager.put("Menu.font", font);
        UIManager.put("MenuBar.font", font);
        UIManager.put("MenuItem.font", font);
        UIManager.put("OptionPane.font", font);
        UIManager.put("Panel.font", font);
        UIManager.put("PasswordField.font", font);
        UIManager.put("PopupMenu.font", font);
        UIManager.put("ProgressBar.font", font);
        UIManager.put("RadioButton.font", font);
        UIManager.put("RadioButtonMenuItem.font", font);
        UIManager.put("ScrollPane.font", font);
        UIManager.put("Slider.font", font);
        UIManager.put("Spinner.font", font);
        UIManager.put("TabbedPane.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TitledBorder.font", font);
        UIManager.put("ToggleButton.font", font);
        UIManager.put("ToolBar.font", font);
        UIManager.put("ToolTip.font", font);
        UIManager.put("Tree.font", font);
        UIManager.put("Viewport.font", font);
    }

}
