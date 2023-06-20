package me.tapeline.hummingbird.ide.expansion.plugins;

import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import org.jetbrains.annotations.Nullable;
import org.pf4j.Plugin;

public abstract class AbstractPlugin extends Plugin {

    public abstract void onLoad(Application application);
    public abstract void onUnload(Application application);

    public abstract void onEnableFor(EditorWindow editor);
    public abstract void onDisableFor(EditorWindow editor);

    public abstract @Nullable PluginFileTypeProvider getFileTypeProvider();
    public abstract @Nullable PluginSyntaxProvider getSyntaxProvider();
    public abstract @Nullable PluginThemeProvider getThemeProvider();
    public abstract @Nullable PluginToolTabsProvider getToolTabsProvider();

}
