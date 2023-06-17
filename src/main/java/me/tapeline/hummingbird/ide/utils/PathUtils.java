package me.tapeline.hummingbird.ide.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathUtils {

    public static List<File> split(File path) {
        List<File> result = new ArrayList<>();
        if (path.getParentFile() != null)
            result.addAll(split(path.getParentFile()));
        result.add(path);
        return result;
    }

    public static List<File> split(File path, File threshold) {
        List<File> result = new ArrayList<>();
        if (path.getParentFile() != null && !threshold.getAbsoluteFile().equals(path.getAbsoluteFile()))
            result.addAll(split(path.getParentFile(), threshold));
        result.add(path);
        return result;
    }

}
