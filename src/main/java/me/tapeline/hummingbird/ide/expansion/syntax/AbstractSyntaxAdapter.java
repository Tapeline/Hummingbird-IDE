package me.tapeline.hummingbird.ide.expansion.syntax;

import me.tapeline.hummingbird.ide.expansion.RegistryEntry;
import me.tapeline.hummingbird.ide.utils.Bounds;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMaker;
import org.fife.ui.rsyntaxtextarea.parser.Parser;

import java.util.List;

public abstract class AbstractSyntaxAdapter {

    public abstract List<Bounds> getReferencableTokens(SyntaxFileContext context);
    public abstract void referenceToken(SyntaxFileContext context, Bounds targetToken);

    public abstract TokenMaker getTokenParser(SyntaxFileContext context);

    public abstract Parser getValidator(SyntaxFileContext context);


}
