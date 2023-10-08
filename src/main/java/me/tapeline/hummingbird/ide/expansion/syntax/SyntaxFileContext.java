package me.tapeline.hummingbird.ide.expansion.syntax;

import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.project.Project;
import me.tapeline.hummingbird.ide.ui.tabs.DefaultCodeEditorTab;

import java.io.File;

public class SyntaxFileContext {

    private final EditorWindow editor;
    private final DefaultCodeEditorTab editorTab;
    private final Project project;
    private final File file;
    private final String text;

    public SyntaxFileContext(EditorWindow editor, DefaultCodeEditorTab editorTab,
                             Project project, File file, String text) {
        this.editor = editor;
        this.editorTab = editorTab;
        this.project = project;
        this.file = file;
        this.text = text;
    }

    public Project getProject() {
        return project;
    }

    public File getFile() {
        return file;
    }

    public String getText() {
        return text;
    }

    public EditorWindow getEditor() {
        return editor;
    }

    public DefaultCodeEditorTab getEditorTab() {
        return editorTab;
    }

    public SyntaxFileContext deriveForText(String text) {
        return new SyntaxFileContext(editor, editorTab, project, file, text);
    }

}
