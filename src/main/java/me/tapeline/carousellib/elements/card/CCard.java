package me.tapeline.carousellib.elements.card;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CCard extends JPanel {

    private JLabel title;
    private JComponent content;

    public CCard(Icon icon, String title) {
        this(icon, title, null);
    }

    public CCard(String title) {
        this(null, title, null);
    }

    public CCard(String title, JComponent content) {
        this(null, title, content);
    }

    public CCard(Icon icon, String title, JComponent content) {
        super();
        this.title = new JLabel();
        this.content = content;

        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        this.title.setVerticalTextPosition(SwingConstants.BOTTOM);
        if (title != null)
            this.title.setText(title);
        if (icon != null)
            this.title.setIcon(icon);

        add(this.title, BorderLayout.PAGE_START);
        if (content != null) {
            add(content, BorderLayout.CENTER);
            content.setBorder(new EmptyBorder(4, 0, 10, 0));
        }

        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public JLabel getTitle() {
        return title;
    }

    public JComponent getContent() {
        return content;
    }

}
