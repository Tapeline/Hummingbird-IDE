package me.tapeline.carousellib.elements.link.linkpanel;

import java.awt.event.ActionEvent;

public class LinkPanelActionEvent extends ActionEvent {

    private final int linkId;
    private final CLinkPanel panel;

    public LinkPanelActionEvent(Object source, CLinkPanel panel, int linkId) {
        super(source, ACTION_PERFORMED, "click");
        this.linkId = linkId;
        this.panel = panel;
    }

    public int getLinkIndex() {
        return linkId;
    }

    public CLinkPanel getPanel() {
        return panel;
    }

}
