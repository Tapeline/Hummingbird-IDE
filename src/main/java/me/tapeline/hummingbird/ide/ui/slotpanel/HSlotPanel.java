package me.tapeline.hummingbird.ide.ui.slotpanel;

import me.tapeline.carousellib.elements.slotpanel.CSlotPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HSlotPanel extends CSlotPanel {

    protected List<TabChangeListener> tabChangeListeners = new ArrayList<>();
    protected List<TabsHideListener> tabsHideListeners = new ArrayList<>();
    protected int lastSelected = 0;

    public HSlotPanel() {
        this(BorderLayout.PAGE_START);
    }

    public HSlotPanel(String tabPlacement) {
        super(tabPlacement);
        remove(display);
    }

    public JPanel getDisplay() {
        return display;
    }

    @Override
    protected void tabSelectionChanged(JToggleButton target) {
        for (JToggleButton button : tabButtons)
            if (button != target)
                button.setSelected(false);
        if (target.isSelected()) {
            layout.show(display, tabNames.get(tabButtons.indexOf(target)));
            for (TabChangeListener listener : tabChangeListeners)
                listener.tabChanged(new TabChangeActionEvent(this,
                        tabs.get(tabButtons.indexOf(target))));
            display.setVisible(true);
            lastSelected = tabButtons.indexOf(target);
        } else {
            for (TabsHideListener listener : tabsHideListeners)
                listener.tabsHidden(new TabsHideActionEvent(this));
            display.setVisible(false);
        }
    }

    public List<TabChangeListener> getTabChangeListeners() {
        return tabChangeListeners;
    }

    public List<TabsHideListener> getTabsHideListeners() {
        return tabsHideListeners;
    }

    public void manuallyShow() {
        manuallyChange(lastSelected);
    }

    public void manuallyChange(int tab) {
        layout.show(display, tabNames.get(tab));
        for (TabChangeListener listener : tabChangeListeners)
            listener.tabChanged(new TabChangeActionEvent(this,
                    tabs.get(tab)));
        display.setVisible(true);
        lastSelected = tab;
        for (JToggleButton button : tabButtons)
            button.setSelected(false);
        tabButtons.get(tab).setSelected(true);
    }

    public void manuallyHide() {
        for (JToggleButton button : tabButtons) {
            button.setSelected(false);
            button.setContentAreaFilled(false);
        }
        for (TabsHideListener listener : tabsHideListeners)
            listener.tabsHidden(new TabsHideActionEvent(this));
        display.setVisible(false);
    }

}
