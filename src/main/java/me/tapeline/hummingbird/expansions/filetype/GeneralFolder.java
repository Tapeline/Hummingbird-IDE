package me.tapeline.hummingbird.expansions.filetype;

import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.utils.Utils;

import java.awt.image.BufferedImage;
import java.io.File;

public class GeneralFile extends AbstractFileType {

    public GeneralFile() {
        icon = Icons.file;
    }

    @Override
    public boolean appliesToFile(File file) {
        if (extensions.size() == 0) return true;
        String ext = Utils.getExtension(file);
        for (String e : extensions)
            if (e.equals(ext))
                return true;
        return false;
    }

}
