package me.tapeline.hummingbird.ide.expansion.plugins;

import me.tapeline.hummingbird.ide.expansion.files.AbstractFileType;

import java.util.List;

public interface PluginFileTypeProvider {

    List<AbstractFileType> providedFileTypes();

}
