package me.tapeline.carousellib.elements.slotpanel;

import me.tapeline.carousellib.elements.actionbar.CActionBar;
import me.tapeline.carousellib.elements.actionbar.CButtonAction;
import me.tapeline.carousellib.elements.verticaltoggle.CVerticalToggleButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CSlotPanel extends JPanel {

    protected CActionBar tabBar;
    protected CardLayout layout;
    protected List<JPanel> tabs;
    protected List<String> tabNames;
    protected List<JToggleButton> tabButtons;
    protected JPanel display;
    protected final String tabPlacement;

    public CSlotPanel() {
        this(BorderLayout.PAGE_START);
    }

    public CSlotPanel(String tabPlacement) {
        super();
        this.tabPlacement = tabPlacement;
        if (tabPlacement.equals(BorderLayout.LINE_START) ||
            tabPlacement.equals(BorderLayout.LINE_END))
            tabBar = new CActionBar(BoxLayout.PAGE_AXIS);
        else
            tabBar = new CActionBar(BoxLayout.LINE_AXIS);
        tabBar.setBorder(new EmptyBorder(0, 0, 0, 0));
        layout = new CardLayout();
        display = new JPanel();
        display.setLayout(layout);
        setLayout(new BorderLayout());
        tabs = new ArrayList<>();
        tabNames = new ArrayList<>();
        tabButtons = new ArrayList<>();
        add(tabBar, tabPlacement);
        add(display, BorderLayout.CENTER);
    }

    public void addTab(Icon icon, String name, JPanel tab) {
        tabs.add(tab);
        tabNames.add(name);
        JToggleButton button;
        if (tabPlacement.equals(BorderLayout.LINE_START))
            button = new CVerticalToggleButton(icon, name, false);
        else if (tabPlacement.equals(BorderLayout.LINE_END))
            button = new CVerticalToggleButton(icon, name, true);
        else
            button = new JToggleButton(name, icon);
        tabButtons.add(button);
        button.addActionListener(e -> tabSelectionChanged(button));
        tabBar.add(button);
        display.add(tab, name);
    }

    public void removeTab(int index) {
        display.remove(tabs.get(index));
        tabs.remove(index);
        tabNames.remove(index);
        tabBar.remove(tabButtons.get(index));
        tabButtons.remove(index);
    }

    public JPanel getTab(int index) {
        return tabs.get(index);
    }

    public int getTabCount() {
        return tabs.size();
    }

    protected void tabSelectionChanged(JToggleButton target) {
        for (JToggleButton button : tabButtons)
            if (button != target)
                button.setSelected(false);
        if (target.isSelected()) {
            layout.show(display, tabNames.get(tabButtons.indexOf(target)));
            add(display);
            display.setVisible(true);
        } else {
            display.setVisible(false);
            remove(display);
        }
        revalidate();
        repaint();
    }

    public boolean isHidden() {
        return display.isVisible();
    }

    public void setHidden(boolean isHidden) {
        display.setVisible(isHidden);
    }

}
