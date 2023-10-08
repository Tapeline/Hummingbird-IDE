package me.tapeline.hummingbird.ide.ui.autocompletion;

import me.tapeline.hummingbird.ide.expansion.syntax.CompletionSuggestion;
import me.tapeline.hummingbird.ide.ui.syntaxtextarea.HSyntaxTextArea;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class AutocompletionPanel extends JDialog {

    public static int PANEL_WIDTH = 400;
    public static int PANEL_HEIGHT = 250;

    private JList<CompletionSuggestion> suggestions;
    private JScrollPane scrollPane;

    public AutocompletionPanel(HSyntaxTextArea area, List<CompletionSuggestion> suggestions) {
        super();
        this.suggestions = new JList<>();
        this.suggestions.setListData(new Vector<>(suggestions
                .stream().sorted(Comparator.comparingInt(CompletionSuggestion::getRelevance)).toList()));
        this.suggestions.setCellRenderer(new CompletionListRenderer());
        if (suggestions.size() > 0)
            this.suggestions.setSelectedIndex(0);
        this.scrollPane = new JScrollPane(this.suggestions);
        add(scrollPane);
        scrollPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) { }

            @Override
            public void windowLostFocus(WindowEvent e) {
                dispose();
            }
        });
        this.suggestions.addKeyListener(new KeyAdapter() {

            private void handle(KeyEvent e) {
                if (StringUtils.isAlphanumeric("" + e.getKeyChar()))
                    area.dispatchEvent(e);
                else if (e.getKeyChar() == KeyEvent.VK_DOWN) {
                    if (AutocompletionPanel.this.suggestions.getSelectedIndex() + 1 < suggestions.size())
                        AutocompletionPanel.this.suggestions.setSelectedIndex(
                                AutocompletionPanel.this.suggestions.getSelectedIndex() + 1);
                } else if (e.getKeyChar() == KeyEvent.VK_UP) {
                    if (AutocompletionPanel.this.suggestions.getSelectedIndex() > 0)
                        AutocompletionPanel.this.suggestions.setSelectedIndex(
                                AutocompletionPanel.this.suggestions.getSelectedIndex() - 1);
                } else if (e.getKeyChar() == '\n') {
                    applyTabSuggestion(area, AutocompletionPanel.this.suggestions.getSelectedValue());
                    dispose();
                } else if (e.getKeyChar() == KeyEvent.VK_TAB) {
                    applyTabSuggestion(area, AutocompletionPanel.this.suggestions.getSelectedValue());
                    dispose();
                } else dispose();
            }
            @Override
            public void keyTyped(KeyEvent e) {
                handle(e);
            }
        });
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setResizable(false);
        setUndecorated(true);
        setAlwaysOnTop(true);
    }

    public void applyTabSuggestion(HSyntaxTextArea area, CompletionSuggestion suggestion) {
        area.replaceRange(suggestion.getText(), suggestion.getBounds().getStart(),
                suggestion.getBounds().getEnd());
    }

    public void applyEnterSuggestion(HSyntaxTextArea area, CompletionSuggestion suggestion) {
        area.insert(suggestion.getText(), suggestion.getBounds().getEnd());
    }

}
