package me.tapeline.hummingbird.plugins;

import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.expansion.plugins.*;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class TestPlugin extends AbstractPlugin {

    @Override
    public void onLoad(Application application) {
        application.getLogger().info("Hello! I'm loaded!");
    }

    @Override
    public void onUnload(Application application) {

    }

    @Override
    public void onEnableFor(EditorWindow editor) {

    }

    @Override
    public void onDisableFor(EditorWindow editor) {

    }

    @Override
    public @Nullable PluginFileTypeProvider getFileTypeProvider() {
        return null;
    }

    @Override
    public @Nullable PluginSyntaxProvider getSyntaxProvider() {
        return null;
    }

    @Override
    public @Nullable PluginThemeProvider getThemeProvider() {
        return () -> Collections.singletonList(new LightTheme());
    }

    @Override
    public @Nullable PluginToolTabsProvider getToolTabsProvider() {
        return null;
    }

}
