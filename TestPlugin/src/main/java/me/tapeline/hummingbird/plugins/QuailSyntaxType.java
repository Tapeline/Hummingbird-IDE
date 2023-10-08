package me.tapeline.hummingbird.plugins;

import me.tapeline.carousellib.utils.Utils;
import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapter;
import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapterType;
import me.tapeline.hummingbird.ide.project.Project;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class QuailSyntaxType extends AbstractSyntaxAdapterType {

    @Override
    public String id() {
        return "quail";
    }

    @Override
    public boolean appliesForFile(Project project, File file) {
        return Utils.getExtension(file).equals("q");
    }

    @Override
    public AbstractSyntaxAdapter constructAdapter(Project project) {
        return new QuailSyntaxAdapter();
    }

}
