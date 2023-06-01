package me.tapeline.carousellib.elements.closabletabs;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TabCloseEvent extends ActionEvent {

    protected final ClosableTab tab;

    public TabCloseEvent(Object source, ClosableTab tab) {
        super(source, ActionEvent.ACTION_PERFORMED, "closed");
        this.tab = tab;
    }

    public ClosableTab getTab() {
        return tab;
    }

}
