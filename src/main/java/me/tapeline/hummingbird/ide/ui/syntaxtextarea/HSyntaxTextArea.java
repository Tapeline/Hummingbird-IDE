package me.tapeline.hummingbird.ide.ui.syntaxtextarea;

import me.tapeline.hummingbird.ide.Registry;
import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapter;
import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapterType;
import me.tapeline.hummingbird.ide.expansion.syntax.SyntaxFileContext;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.tabs.DefaultCodeEditorTab;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Style;
import org.fife.ui.rsyntaxtextarea.TokenMaker;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HSyntaxTextArea extends RSyntaxTextArea {

    protected List<AbstractSyntaxAdapter> applicableAdapters = new ArrayList<>();
    protected File file;
    protected Project project;
    protected SyntaxFileContext context;
    protected ContextRefreshingThread contextRefreshingThread;

    public HSyntaxTextArea() {}

    public HSyntaxTextArea(RSyntaxDocument doc) {
        super(doc);
    }

    public HSyntaxTextArea(String text) {
        super(text);
    }

    public HSyntaxTextArea(int rows, int cols) {
        super(rows, cols);
    }

    public HSyntaxTextArea(String text, int rows, int cols) {
        super(text, rows, cols);
    }

    public HSyntaxTextArea(RSyntaxDocument doc, String text, int rows, int cols) {
        super(doc, text, rows, cols);
    }

    public HSyntaxTextArea(int textMode) {
        super(textMode);
    }

    public void initArea(EditorWindow editorWindow, DefaultCodeEditorTab tab, Project project, File file) {
        this.project = project;
        this.file = file;

        context = new SyntaxFileContext(editorWindow, tab, project, file, getText());

        for (AbstractSyntaxAdapterType adapterType : Registry.syntaxAdapterTypes)
            if (adapterType.appliesForFile(project, file))
                applicableAdapters.add(adapterType.constructAdapter(project));

        contextRefreshingThread = new ContextRefreshingThread(this);
        contextRefreshingThread.start();
    }

    public void setTokenMaker(TokenMaker tokenMaker) {
        RSyntaxDocument doc = (RSyntaxDocument)getDocument();
        String oldStyle = getSyntaxEditingStyle();
        ((RSyntaxDocument) getDocument()).setSyntaxStyle(tokenMaker);
        try {
            FieldUtils.writeField(
                    this,
                    "syntaxStyleKey",
                    "text/unknown",
                    true
            );
        } catch (IllegalAccessException e) { e.printStackTrace(); }
        firePropertyChange(SYNTAX_STYLE_PROPERTY, oldStyle, "text/unknown");
        setActiveLineRange(-1, -1);
    }

}
