package me.tapeline.hummingbird.ide.expansion.plugins;

import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapter;

import java.util.List;

public interface PluginSyntaxProvider {

    List<AbstractSyntaxAdapter> providedAdapters();

}
