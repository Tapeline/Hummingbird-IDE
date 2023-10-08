package me.tapeline.hummingbird.ide.ui.tabs;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.carousellib.configuration.exceptions.FieldNotFoundException;
import me.tapeline.carousellib.dialogs.Dialogs;
import me.tapeline.hummingbird.ide.Application;
import me.tapeline.hummingbird.ide.FS;
import me.tapeline.hummingbird.ide.Registry;
import me.tapeline.hummingbird.ide.expansion.syntax.CompletionSuggestion;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.autocompletion.AutocompletionPanel;
import me.tapeline.hummingbird.ide.ui.syntaxtextarea.HSyntaxTextArea;
import me.tapeline.hummingbird.ide.utils.Bounds;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.text.Segment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class DefaultCodeEditorTab extends AbstractWorkspaceTab implements FileReferencingTab {

    protected RTextScrollPane scrollPane;
    protected HSyntaxTextArea textArea;
    protected File file;
    protected String previouslySavedText;
    protected EditorWindow editor;

    public DefaultCodeEditorTab(EditorWindow editor, File file) {
        super("DefaultCodeEditor_" + file.getAbsolutePath());
        this.file = file;
        this.editor = editor;
        this.textArea = new HSyntaxTextArea();
        this.scrollPane = new RTextScrollPane(textArea);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        textArea.setBackground(UIManager.getColor("TextArea.background"));
        scrollPane.getGutter().setBackground(UIManager.getColor("ScrollBar.darkShadow"));
        scrollPane.getGutter().setBorderColor(UIManager.getColor("Panel.background"));
        textArea.setSelectionColor(UIManager.getColor("TextArea.selectionBackground"));
        textArea.setSelectedTextColor(UIManager.getColor("TextArea.selectionForeground"));
        if (Registry.currentTheme.isDark())
            textArea.setCurrentLineHighlightColor(UIManager.getColor("TextArea.background").brighter());
        else
            textArea.setCurrentLineHighlightColor(UIManager.getColor("TextArea.background").darker());
        textArea.getSyntaxScheme().getStyle(TokenTypes.IDENTIFIER).foreground =
                UIManager.getColor("TextArea.foreground");

        int tabSize = 4;
        try {
            tabSize = Application.instance.getConfiguration().editor().getInt("spacesInTab");
        } catch (FieldNotFoundException ignored) { }
        textArea.setTabSize(tabSize);
        textArea.setTabsEmulated(true);
        textArea.setAutoIndentEnabled(true);
        KeyStroke keyStroke = KeyStroke.getKeyStroke("ctrl SPACE");
        textArea.getInputMap().put(keyStroke, "manualAutocompletionRequest");
        textArea.getActionMap().put("manualAutocompletionRequest", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = textArea.getCaret().getMagicCaretPosition().x;
                int y = textArea.getCaret().getMagicCaretPosition().y;
                AutocompletionPanel panel = new AutocompletionPanel(textArea, Arrays.asList(
                        new CompletionSuggestion(null, "Test", "com.test", 1,
                                new Bounds(0, 1))
                ));
                Point location = textArea.getLocation();
                SwingUtilities.convertPointToScreen(location, textArea);
                panel.setLocation(((int) location.getX()) + x + 20, ((int) location.getY()) + y + 20);
                panel.setVisible(true);
            }
        });

        Font font = new Font("Consolas", Font.PLAIN, 16);
        try {
            String family = Application.instance.getConfiguration().editor().getString("font");
            int size = Application.instance.getConfiguration().editor().getInt("fontSize");
            font = new Font(family, Font.PLAIN, size);
        } catch (FieldNotFoundException ignored) {}
        textArea.setFont(font);

        /*textArea.setTokenMaker(new TokenMakerBase() {
            private int currentTokenType;
            private int currentTokenStart;

            @Override
            public Token getTokenList(Segment text, int startTokenType, int startOffset) {
                resetTokenList();
                char[] array = text.array;
                int offset = text.offset;
                int count = text.count;
                int end = offset + count;

                // Token starting offsets are always of the form:
                // 'startOffset + (currentTokenStart-offset)', but since startOffset and
                // offset are constant, tokens' starting positions become:
                // 'newStartOffset+currentTokenStart'.
                int newStartOffset = startOffset - offset;

                currentTokenStart = offset;
                currentTokenType = startTokenType;

                for (int i = offset; i < end; i++) {
                    char c = array[i];

                    switch (currentTokenType) {
                        case Token.NULL: {
                            currentTokenStart = i;   // Starting a new token here.
                            switch (c) {
                                case ' ':
                                case '\t':
                                    currentTokenType = Token.WHITESPACE;
                                    break;
                                case '"':
                                    currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                                    break;
                                case '#':
                                    currentTokenType = Token.COMMENT_EOL;
                                    break;
                                default:
                                    if (RSyntaxUtilities.isDigit(c)) {
                                        currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
                                        break;
                                    } else if (RSyntaxUtilities.isLetter(c) || c == '_') {
                                        currentTokenType = Token.IDENTIFIER;
                                        break;
                                    }
                                    // Anything not currently handled - mark as an identifier
                                    currentTokenType = Token.IDENTIFIER;
                                    break;
                            }
                            break;
                        }
                        case Token.WHITESPACE: {
                            switch (c) {
                                case ' ':
                                case '\t':
                                    break;   // Still whitespace.
                                case '"':
                                    addToken(text, currentTokenStart, i - 1, Token.WHITESPACE,
                                            newStartOffset + currentTokenStart);
                                    currentTokenStart = i;
                                    currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                                    break;
                                case '#':
                                    addToken(text, currentTokenStart, i - 1,
                                            Token.WHITESPACE, newStartOffset + currentTokenStart);
                                    currentTokenStart = i;
                                    currentTokenType = Token.COMMENT_EOL;
                                    break;
                                default:   // Add the whitespace token and start a new.
                                    addToken(text, currentTokenStart, i - 1, Token.WHITESPACE,
                                            newStartOffset + currentTokenStart);
                                    currentTokenStart = i;
                                    if (RSyntaxUtilities.isDigit(c)) {
                                        currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
                                        break;
                                    } else if (RSyntaxUtilities.isLetter(c) || c == '/' || c == '_') {
                                        currentTokenType = Token.IDENTIFIER;
                                        break;
                                    }
                                    // Anything not currently handled - mark as identifier
                                    currentTokenType = Token.IDENTIFIER;
                            }
                            break;
                        }
                        default:
                        case Token.IDENTIFIER: {
                            switch (c) {
                                case ' ':
                                case '\t':
                                    addToken(text, currentTokenStart, i - 1, Token.IDENTIFIER, newStartOffset + currentTokenStart);
                                    currentTokenStart = i;
                                    currentTokenType = Token.WHITESPACE;
                                    break;
                                case '"':
                                    addToken(text, currentTokenStart, i - 1, Token.IDENTIFIER, newStartOffset + currentTokenStart);
                                    currentTokenStart = i;
                                    currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                                    break;
                                default:
                                    if (RSyntaxUtilities.isLetterOrDigit(c) || c == '/' || c == '_') {
                                        break;   // Still an identifier of some type.
                                    }
                                    // Otherwise, we're still an identifier (?).

                            } // End of switch (c).

                            break;
                        }
                        case Token.LITERAL_NUMBER_DECIMAL_INT: {

                            switch (c) {

                                case ' ':
                                case '\t':
                                    addToken(text, currentTokenStart, i - 1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset + currentTokenStart);
                                    currentTokenStart = i;
                                    currentTokenType = Token.WHITESPACE;
                                    break;

                                case '"':
                                    addToken(text, currentTokenStart, i - 1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset + currentTokenStart);
                                    currentTokenStart = i;
                                    currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
                                    break;

                                default:

                                    if (RSyntaxUtilities.isDigit(c)) {
                                        break;   // Still a literal number.
                                    }

                                    // Otherwise, remember this was a number and start over.
                                    addToken(text, currentTokenStart, i - 1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset + currentTokenStart);
                                    i--;
                                    currentTokenType = Token.NULL;

                            } // End of switch (c).

                            break;
                        }
                        case Token.COMMENT_EOL: {
                            i = end - 1;
                            addToken(text, currentTokenStart, i, currentTokenType, newStartOffset + currentTokenStart);
                            // We need to set token type to null so at the bottom we don't add one more token.
                            currentTokenType = Token.NULL;
                            break;
                        }
                        case Token.LITERAL_STRING_DOUBLE_QUOTE: {
                            if (c == '"') {
                                addToken(text, currentTokenStart, i,
                                        Token.LITERAL_STRING_DOUBLE_QUOTE,
                                        newStartOffset + currentTokenStart);
                                currentTokenType = Token.NULL;
                            }
                            break;
                        }
                    }

                }

                switch (currentTokenType) {
                    // Remember what token type to begin the next line with.
                    case Token.LITERAL_STRING_DOUBLE_QUOTE:
                        addToken(text,
                                currentTokenStart,
                                end - 1,
                                Token.LITERAL_STRING_DOUBLE_QUOTE,
                                newStartOffset + currentTokenStart);
                        break;
                    // Do nothing if everything was okay.
                    case Token.NULL:
                        addNullToken();
                        break;
                    // All other token types don't continue to the next line...
                    default:
                        addToken(text, currentTokenStart,end-1, currentTokenType,
                                newStartOffset+currentTokenStart);
                        addNullToken();
                }

                // Return the first token in our linked list.
                return firstToken;

            }
        });*/

        String text = FS.readFile(file);
        textArea.setText(text);
        previouslySavedText = text;
        textArea.initArea(editor, this, editor.getProject(), file);
    }

    @Override
    public void save() {
        FS.writeFile(file, textArea.getText());
        previouslySavedText = textArea.getText();
    }

    @Override
    public void reload() {
        String currentContents = FS.readFile(file);
        if (currentContents == null) {
            Dialogs.error(editor, "Cannot reload file", "File couldn't be reloaded");
            return;
        }
        if (!currentContents.equals(previouslySavedText)) {
            boolean replace = Dialogs.confirmYesNo(editor, "Conflict",
                    """
                            File was modified by another program.
                            Load it now?
                            You will lose your changes.""");
            if (replace)
                textArea.setText(currentContents);
        }
    }

    @Override
    public File getFile() {
        return file;
    }

}
