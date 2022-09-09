package me.tapeline.hummingbird.expansions;

import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.projecttype.AbstractProjectType;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;

import java.util.List;

public abstract class Plugin {

    public abstract String name();
    public abstract String author();
    public abstract float version();

    public abstract List<AbstractProjectType> providedProjectTypes();
    public abstract List<AbstractSyntaxHighlighter> providedSyntaxes();
    public abstract List<AbstractSyntaxChecker> providedSyntaxCheckers();
    public abstract List<AbstractFileType> providedFileTypes();
    public abstract List<AbstractTheme> providedThemes();

}
