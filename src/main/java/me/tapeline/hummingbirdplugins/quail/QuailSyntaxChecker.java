package me.tapeline.hummingbirdplugins.quail;

import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.Bounds;
import me.tapeline.hummingbird.expansions.highlighter.Highlight;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;

import javax.swing.*;
import java.util.*;

public class QuailSyntaxChecker extends AbstractSyntaxChecker {

    private int start = 0;
    private int current = 0;
    private int line = 1;
    private String source;
    private List<SyntaxTip> tokens = new ArrayList<>();
    private AbstractColorScheme colors;

    private Map<String, TextStyle> keywords;

    @Override
    public AbstractFileType getApplicableFileType() {
        return new QuailFileType();
    }

    @Override
    public List<SyntaxTip> check(JTextPane pane) {
        source = pane.getText();
        colors = Registry.currentTheme.scheme();

        tokens = new ArrayList<>();

        start = 0;
        current = 0;
        line = 1;

        keywords = new HashMap<>();
        List<String> kw = Arrays.asList(
                "func", "string", "bool", "num", "container", "list", "metacontainer",
                "object", "function", "method", "staticmethod", "class", "anonymous",
                "require", "void", "anyof", "local", "final", "static", "do", "does", "end",
                "then", "has", "with", "as", "through", "if", "elseif", "else", "try", "catch",
                "while", "loop", "stop when", "every", "on", "when", "override", "gets", "sets",
                "is type of", "instanceof", "and", "or", "in", "is same type as", "of", "filter",
                "in power of", "plus", "minus", "divided by", "multiplied by", "is greater than",
                "is less than", "is greater or equal to", "is less or equal to", "is", "step",
                "should have", "should be", "should now be", "should now be set", "should be set", "'s",
                "not", "negate", "notnull", "exists", "assert", "use", "throw", "using", "deploy",
                "strike", "return", "breakpoint", "break", "continue", "memory"
        );
        for (String k : kw)
            keywords.put(k, colors.keyword2);

        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void addToken(String s, SyntaxTip.TipType type) {
        tokens.add(new SyntaxTip(new Bounds(start, current), s, type));
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
            case ')':
            case '{':
            case '}':
            case '[':
            case ']':
            case ':':
            case '+':
            case '-':
            case '/':
            case '*':
            case '=':
            case '>':
            case '<':
            case ',':
            case ';':
            case '.': {
                //addToken(colors.operator);
                break;
            }

            case '#': {
                boolean isDoc = false;
                if (peekNext() == '~')
                    isDoc = true;
                while (peek() != '\n' && !isAtEnd()) advance();
                //addToken(isDoc? colors.commentDoc : colors.comment);
                break;
            }
            case '@': {
                scanId(true);
                break;
            }
            case '"': scanString(); break;
            default: {
                if (isDigit(c)) {
                    scanNumber();
                } else if (isAlpha(c)) {
                    scanId(false);
                }
            }
        }
    }

    private void scanString() {
        if (!isAtEnd())
            while (advance() != '"')
                if (isAtEnd()) break;
        //addToken(colors.string);
    }

    private void scanNumber() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) advance();
        }

        //addToken(colors.number);
    }

    private void scanId(boolean metadata) {
        if (metadata) {
            while (isAlphaNumeric(peek())) advance();
            String text = source.substring(start, current);
            if (!isGoodId(text))
                addToken("Writing _ in IDs is a bad idea:\nQEP A1", SyntaxTip.TipType.CODE_STYLE);
            return;
        }

        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TextStyle type = keywords.get(text);
        if (type == null) type = colors.regular;
        if (!isGoodId(text))
            addToken("Writing _ in IDs is a bad idea:\nQEP A1", SyntaxTip.TipType.CODE_STYLE);
    }

    private boolean isGoodId(String s) {
        return !s.contains("_");
    }
}
