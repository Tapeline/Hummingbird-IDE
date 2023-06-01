package me.tapeline.carousellib.elements.link.linkpanel;

import java.awt.event.ActionEvent;

public class LinkPanelActionEvent extends ActionEvent {

    private final int linkId;

    public LinkPanelActionEvent(Object source, int linkId) {
        super(source, ACTION_PERFORMED, "click");
        this.linkId = linkId;
    }

    public int getLinkIndex() {
        return linkId;
    }

}
