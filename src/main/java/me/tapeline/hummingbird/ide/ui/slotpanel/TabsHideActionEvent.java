package me.tapeline.hummingbird.ide.ui.slotpanel;

import java.awt.event.ActionEvent;

public class TabsHideActionEvent extends ActionEvent {

    public TabsHideActionEvent(Object source) {
        super(source, ActionEvent.ACTION_PERFORMED, "hidden");
    }

}
