package me.tapeline.hummingbird.ui.jcodeeditor;

import javax.swing.*;

public class JRichScrollPane extends JScrollPane {

    public TextLineNumber numbering;
    public JCodeEditor editor;

    public JRichScrollPane(JCodeEditor editor) {
        super(editor);
        this.editor = editor;
        numbering = new TextLineNumber(editor);
        setRowHeaderView(numbering);
    }

}
