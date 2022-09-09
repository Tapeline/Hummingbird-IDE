package me.tapeline.hummingbirdplugins.quail;

import me.tapeline.hummingbird.expansions.Plugin;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.projecttype.AbstractProjectType;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuailPlugin extends Plugin {
    @Override
    public String name() {
        return "Quail Support";
    }

    @Override
    public String author() {
        return "Tapeline";
    }

    @Override
    public float version() {
        return 1;
    }

    @Override
    public List<AbstractProjectType> providedProjectTypes() {
        return new ArrayList<>();
    }

    @Override
    public List<AbstractSyntaxHighlighter> providedSyntaxes() {
        return Arrays.asList(new QuailSyntaxHighlighter());
    }

    @Override
    public List<AbstractSyntaxChecker> providedSyntaxCheckers() {
        return Arrays.asList(new QuailSyntaxChecker());
    }

    @Override
    public List<AbstractFileType> providedFileTypes() {
        return Arrays.asList(new QuailFileType());
    }

    @Override
    public List<AbstractTheme> providedThemes() {
        return new ArrayList<>();
    }
}
