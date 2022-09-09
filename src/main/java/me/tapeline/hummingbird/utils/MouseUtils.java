package me.tapeline.hummingbird.utils;

import java.awt.event.MouseEvent;

public class MouseUtils {

    public static boolean isLeftClick(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON1;
    }

    public static boolean isLeftDoubleClick(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2;
    }

    public static boolean isRightClick(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON3;
    }

    public static boolean isRightDoubleClick(MouseEvent e) {
        return e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2;
    }

}
