package me.tapeline.carousellib.elements.slotpanel;

import javax.swing.*;
import java.awt.*;

public class CSimpleTab extends JPanel {

    private JComponent content;

    public CSimpleTab(JComponent element) {
        content = element;
        setLayout(new BorderLayout());
        add(content, BorderLayout.CENTER);
    }

    public JComponent getContent() {
        return content;
    }

}
