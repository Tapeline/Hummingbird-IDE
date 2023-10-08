package me.tapeline.hummingbird.ide.expansion.plugins;

import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapter;
import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapterType;

import java.util.List;

public interface PluginSyntaxProvider {

    List<AbstractSyntaxAdapterType> providedAdapters();

}
