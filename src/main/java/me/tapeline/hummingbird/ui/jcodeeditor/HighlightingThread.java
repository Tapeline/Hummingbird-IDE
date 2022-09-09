package me.tapeline.hummingbird.ui.jcodeeditor;

import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.highlighter.Highlight;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;

import javax.swing.text.StyledDocument;
import java.util.ArrayList;
import java.util.List;

public class HighlightingThread extends Thread {

    public JCodeEditor codeEditor;

    public HighlightingThread(JCodeEditor codeEditor) {
        this.codeEditor = codeEditor;
    }

    @Override
    public void run() {
        List<AbstractSyntaxHighlighter> syntaxHighlighters = new ArrayList<>();
        for (AbstractSyntaxHighlighter ash : Registry.syntaxHighlighters)
            if (ash.getApplicableFileType().appliesToFile(codeEditor.tab.file))
                syntaxHighlighters.add(ash);

        for (AbstractSyntaxHighlighter ash : syntaxHighlighters) {
            List<Highlight> highlights = ash.highlight(codeEditor);
            for (Highlight highlight : highlights) {
                StyledDocument document = codeEditor.getStyledDocument();
                TextStyle newStyle = highlight.style;
                int start = highlight.bounds.left;
                int end = highlight.bounds.right;
                //boolean replaceOld = styleName.equals("normal");
                for (int i = 0; i < 2; i++) {
                    document.setCharacterAttributes(
                            start,
                            end - start,
                            newStyle.attributeSet(),
                            i != 0
                    );
                }
            }
        }

        codeEditor.tipList = new ArrayList<>();

        List<AbstractSyntaxChecker> syntaxCheckers = new ArrayList<>();
        for (AbstractSyntaxChecker ash : Registry.syntaxCheckers)
            if (ash.getApplicableFileType().appliesToFile(codeEditor.tab.file))
                syntaxCheckers.add(ash);

        for (AbstractSyntaxChecker ash : syntaxCheckers) {
            List<SyntaxTip> tips = ash.check(codeEditor);
            for (SyntaxTip tip : tips) {
                StyledDocument document = codeEditor.getStyledDocument();
                TextStyle newStyle = tip.style();
                int start = tip.bounds.left;
                int end = tip.bounds.right;
                //boolean replaceOld = styleName.equals("normal");
                document.setCharacterAttributes(
                        start,
                        end - start,
                        newStyle.attributeSet(),
                        false
                );
                codeEditor.tipList.add(tip);
            }
        }
    }
}
