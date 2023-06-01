package me.tapeline.hummingbird.ide.expansion.plugins;

import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.expansion.RegistryEntry;
import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;
import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapter;
import me.tapeline.hummingbird.ide.expansion.themes.AbstractTheme;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.tooltabs.AbstractToolTab;

import java.util.List;

public abstract class AbstractPlugin implements RegistryEntry {

    public abstract void onLoad(Application application);
    public abstract void onUnload(Application application);

    public abstract void onEnable(EditorWindow editor);
    public abstract void onDisable(EditorWindow editor);

    public abstract List<AbstractFileType> providedFileTypes();

    public abstract List<AbstractTheme> providedThemes();

    public abstract List<AbstractSyntaxAdapter> providedAdapters();

    public abstract List<AbstractToolTab> providedLeftToolTabs();
    public abstract List<AbstractToolTab> providedRightToolTabs();
    public abstract List<AbstractToolTab> providedBottomToolTabs();

}
