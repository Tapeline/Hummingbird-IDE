package me.tapeline.carousellib.elements.crumbdisplay;

import me.tapeline.carousellib.icons.navigation.CLeftIcon;
import me.tapeline.carousellib.icons.navigation.CRightIcon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CCrumbDisplay extends JPanel {

    protected List<JLabel> crumbs = new ArrayList<>();

    public CCrumbDisplay() {
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 4, 4);
        setLayout(layout);
    }

    public void addCrumb(String text) {
        JLabel crumb = new JLabel(text);
        crumb.setIcon(new CRightIcon(10));
        crumbs.add(crumb);
        add(crumb);
    }

    public void removeCrumb(int index) {
        remove(crumbs.get(index));
        crumbs.remove(index);
    }

    public JLabel getCrumb(int index) {
        return crumbs.get(index);
    }

    public List<JLabel> getCrumbs() {
        return crumbs;
    }

}
