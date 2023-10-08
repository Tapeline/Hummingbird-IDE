package me.tapeline.hummingbird.plugins;

import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapter;
import me.tapeline.hummingbird.ide.expansion.syntax.SyntaxFileContext;
import me.tapeline.hummingbird.ide.utils.Bounds;
import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.parser.Parser;

import java.util.List;

public class QuailSyntaxAdapter extends AbstractSyntaxAdapter {

    public QuailTokenMaker tokenMaker = new QuailTokenMaker();

    @Override
    public List<Bounds> getReferencableTokens(SyntaxFileContext context) {
        return null;
    }

    @Override
    public void referenceToken(SyntaxFileContext context, Bounds targetToken) {

    }

    @Override
    public TokenMaker getTokenParser(SyntaxFileContext context) {
        return tokenMaker;
    }

    @Override
    public Parser getValidator(SyntaxFileContext context) {
        return null;
    }

}
