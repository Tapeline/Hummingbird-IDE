package me.tapeline.hummingbird.expansions;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.projecttype.AbstractProjectType;
import me.tapeline.hummingbird.expansions.runcfg.RunConfiguration;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;
import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Registry {

    public static AbstractTheme currentTheme;

    public static List<AbstractTheme> themes = new ArrayList<>();
    public static List<AbstractFileType> fileTypes = new ArrayList<>();
    public static List<AbstractSyntaxChecker> syntaxCheckers = new ArrayList<>();
    public static List<AbstractSyntaxHighlighter> syntaxHighlighters = new ArrayList<>();
    public static List<AbstractProjectType> projectTypes = new ArrayList<>();

    public static void registerPlugin(Plugin pl) {
        if (pl.providedThemes() != null) themes.addAll(pl.providedThemes());
        if (pl.providedFileTypes() != null) fileTypes.addAll(pl.providedFileTypes());
        if (pl.providedSyntaxCheckers() != null) syntaxCheckers.addAll(pl.providedSyntaxCheckers());
        if (pl.providedSyntaxes() != null) syntaxHighlighters.addAll(pl.providedSyntaxes());
        if (pl.providedProjectTypes() != null) projectTypes.addAll(pl.providedProjectTypes());
    }

    public static AbstractTheme getCurrentTheme() {
        for (AbstractTheme t : themes)
            if (t.name().equals(Main.cfg.theme))
                return t;
        return themes.get(0);
    }

    public static void applyTheme(AbstractTheme a) {
        currentTheme = a;
        a.onApply();
    }

    public static void order() {
        fileTypes.sort((o1, o2) -> Integer.compare(o2.weight(), o1.weight()));
    }

}
