package me.tapeline.hummingbird.ide.ui.slotpanel;

import me.tapeline.carousellib.elements.slotpanel.CSlotPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HSlotPanel extends CSlotPanel {

    protected List<TabChangeListener> tabChangeListeners = new ArrayList<>();
    protected List<TabsHideListener> tabsHideListeners = new ArrayList<>();

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

}
