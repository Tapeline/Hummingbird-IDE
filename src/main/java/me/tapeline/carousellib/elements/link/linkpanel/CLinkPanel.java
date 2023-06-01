package me.tapeline.carousellib.elements.link.linkpanel;

import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.link.CLink;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CLinkPanel extends CActionBar {

    private String[] linkTexts;
    private CLink[] links;

    private List<LinkPanelActionListener> listeners = new ArrayList<>();

    public CLinkPanel(String... linkTexts) {
        this.linkTexts = linkTexts;
        this.links = new CLink[linkTexts.length];
        regenerateLinks();
    }

    public void regenerateLinks() {
        for (Component component : getComponents()) {
            if (component instanceof CLink)
                ((CLink) component).getLinkListeners().clear();
            remove(component);
        }
        if (linkTexts.length != links.length)
            this.links = new CLink[linkTexts.length];
        for (int i = 0; i < linkTexts.length; i++)
            links[i] = new CLink(linkTexts[i]);
        for (int i = 0; i < links.length; i++) {
            CLink link = links[i];
            link.setBorder(new EmptyBorder(0, 4, 0, 4));
            final int finalI = i;
            link.addLinkListener(e -> {
                for (LinkPanelActionListener listener : listeners)
                    listener.action(new LinkPanelActionEvent(link, finalI));
            });
            add(link);
        }
    }

    public void setLinkTexts(String[] linkTexts) {
        this.linkTexts = linkTexts;
        regenerateLinks();
    }

    public String[] getLinkTexts() {
        return linkTexts;
    }

    public CLink[] getLinks() {
        return links;
    }

    public void addLinkListener(LinkPanelActionListener listener) {
        listeners.add(listener);
    }

    public void removeLinkListener(LinkPanelActionListener listener) {
        listeners.remove(listener);
    }

    public List<LinkPanelActionListener> getListeners() {
        return listeners;
    }

}
