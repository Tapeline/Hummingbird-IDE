package me.tapeline.hummingbird.ide.ui.tools;

import javax.swing.*;

public class JSplitTools {

    public static double getProportionalDividerLocation(JSplitPane pane) {
        if (pane.getOrientation() == JSplitPane.VERTICAL_SPLIT) {
            return pane.getDividerLocation() / ((double) (pane.getHeight() - pane.getDividerSize()));
        } else {
            return pane.getDividerLocation() / ((double) (pane.getWidth() - pane.getDividerSize()));
        }
    }

}
