package me.tapeline.hummingbird.ide.ui.tooltabs;

import me.tapeline.carousellib.icons.commonactions.CRemoveIcon;
import me.tapeline.hummingbird.ide.frames.editor.EditorWindow;
import me.tapeline.hummingbird.ide.ui.slotpanel.HSlotPanel;

import javax.swing.*;

public class HideTabButton extends JButton {

    public static final int BOTTOM = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private EditorWindow editor;
    private int tabPosition;

    public HideTabButton(EditorWindow editor, int tabPosition) {
        this.editor = editor;
        this.tabPosition = tabPosition;
        addActionListener((e) -> {
            HSlotPanel desiredPanel = null;
            switch (HideTabButton.this.tabPosition) {
                case BOTTOM -> desiredPanel = HideTabButton.this.editor.getStudioPanel().getBottomTabs();
                case LEFT -> desiredPanel = HideTabButton.this.editor.getStudioPanel().getLeftTabs();
                case RIGHT -> desiredPanel = HideTabButton.this.editor.getStudioPanel().getRightTabs();
            }
            if (desiredPanel != null)
                desiredPanel.manuallyHide();
        });
        setIcon(new CRemoveIcon(16, 16));
    }

}
