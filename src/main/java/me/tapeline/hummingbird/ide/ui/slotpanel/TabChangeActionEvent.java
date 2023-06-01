package me.tapeline.hummingbird.ide.ui.slotpanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TabChangeActionEvent extends ActionEvent {

    protected final JPanel tab;

    public TabChangeActionEvent(Object source, JPanel tab) {
        super(source, ActionEvent.ACTION_PERFORMED, "changed");
        this.tab = tab;
    }

    public JPanel getTab() {
        return tab;
    }

}
