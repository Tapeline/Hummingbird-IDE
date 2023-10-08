package me.tapeline.hummingbird.ide.ui.studiopanel;

import me.tapeline.hummingbird.ide.ui.slotpanel.HSlotPanel;
import me.tapeline.hummingbird.ide.ui.tools.JSplitTools;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudioPanel extends JPanel {

    protected final BorderLayout layout;
    protected final JSplitPane topBottomSplitter;
    protected final JSplitPane leftRightSplitter;
    protected final JSplitPane centerRightSplitter;
    protected final HSlotPanel bottomTabs;
    protected final HSlotPanel leftTabs;
    protected final HSlotPanel rightTabs;
    protected final WorkspaceDividerPositions dividerPositions;

    public StudioPanel() {
        layout = new BorderLayout();
        setLayout(layout);
        topBottomSplitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        leftRightSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centerRightSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        bottomTabs = new HSlotPanel(BorderLayout.PAGE_END);
        leftTabs = new HSlotPanel(BorderLayout.LINE_START);
        rightTabs = new HSlotPanel(BorderLayout.LINE_END);
        dividerPositions = new WorkspaceDividerPositions();

        centerRightSplitter.setResizeWeight(1);

        leftTabs.getTabBar().setBorder(new EmptyBorder(0, 0, 0, 4));

        add(leftTabs, BorderLayout.LINE_START);
        add(rightTabs, BorderLayout.LINE_END);
        add(bottomTabs, BorderLayout.PAGE_END);
        add(topBottomSplitter, BorderLayout.CENTER);
        topBottomSplitter.setTopComponent(leftRightSplitter);
        leftRightSplitter.setRightComponent(centerRightSplitter);

        bottomTabs.getTabsHideListeners().add(event -> {
            dividerPositions.setTopBottomPercent(
                    JSplitTools.getProportionalDividerLocation(topBottomSplitter));
            topBottomSplitter.setBottomComponent(null);
        });
        bottomTabs.getTabChangeListeners().add(event -> showBottom());

        leftTabs.getTabsHideListeners().add(event -> {
            dividerPositions.setHorizontalLeftPercent(
                    JSplitTools.getProportionalDividerLocation(leftRightSplitter));
            leftRightSplitter.setLeftComponent(null);
        });
        leftTabs.getTabChangeListeners().add(event -> {
            leftRightSplitter.setLeftComponent(leftTabs.getDisplay());
            leftRightSplitter.setDividerLocation(dividerPositions.getHorizontalLeftPercent());
        });

        rightTabs.getTabsHideListeners().add(event -> {
            dividerPositions.setHorizontalRightPercent(
                    JSplitTools.getProportionalDividerLocation(centerRightSplitter));
            centerRightSplitter.setRightComponent(null);
        });
        rightTabs.getTabChangeListeners().add(event -> {
            centerRightSplitter.setRightComponent(rightTabs.getDisplay());
            centerRightSplitter.setDividerLocation(dividerPositions.getHorizontalRightPercent());
        });
    }

    public void setCentralWidget(JComponent component) {
        centerRightSplitter.setLeftComponent(component);
    }

    public WorkspaceDividerPositions getDividerPositions() {
        return dividerPositions;
    }

    public JSplitPane getTopBottomSplitter() {
        return topBottomSplitter;
    }

    public JSplitPane getLeftRightSplitter() {
        return leftRightSplitter;
    }

    public JSplitPane getCenterRightSplitter() {
        return centerRightSplitter;
    }

    public HSlotPanel getBottomTabs() {
        return bottomTabs;
    }

    public HSlotPanel getLeftTabs() {
        return leftTabs;
    }

    public HSlotPanel getRightTabs() {
        return rightTabs;
    }

    public void showBottom() {
        dividerPositions.setBottomHidden(false);
        topBottomSplitter.setBottomComponent(bottomTabs.getDisplay());
        topBottomSplitter.setDividerLocation(dividerPositions.getTopBottomPercent());
    }

}
