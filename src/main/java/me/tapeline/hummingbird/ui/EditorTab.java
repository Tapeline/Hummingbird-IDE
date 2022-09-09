package me.tapeline.hummingbird.ui;

import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.resources.Fonts;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.jcodeeditor.JCodeEditor;
import me.tapeline.hummingbird.ui.jcodeeditor.JRichScrollPane;
import me.tapeline.hummingbird.windows.forms.editor.EditorFrame;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EditorTab extends JPanel {

    public EditorFrame frame;
    public File file;
    public JRichScrollPane scrollPane;

    public EditorTab(EditorFrame frame, File file, String contents) {
        this.frame = frame;
        this.file = file;

        setLayout(new GridLayout(1, 1));

        JCodeEditor editor = new JCodeEditor(this);
        editor.setFont(Fonts.jbMonoRegular);
        editor.setText(contents);

        scrollPane = new JRichScrollPane(editor);
        /*scrollPane.getGutter().setBookmarkingEnabled(true);
        scrollPane.getGutter().setBookmarkIcon(new ImageIcon(Icons.file));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getGutter().setLineNumberFont(Fonts.jbMonoRegular);
        scrollPane.setFoldIndicatorEnabled(true);
        scrollPane.getGutter().setFoldIcons(new ImageIcon(Icons.iconUnfold),
                new ImageIcon(Icons.iconFold));*/
        add(scrollPane);
    }

    public void save() {
        FS.writeFile(file, scrollPane.editor.getText());
    }
}
