package me.tapeline.hummingbird.windows.forms.welcome;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.utils.Utils;
import me.tapeline.hummingbird.windows.forms.editor.EditorFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class ProjectEntry extends JPanel {

    public String path;
    public JLabel title;
    public JLabel desc;
    public WelcomeScreen screen;

    public ProjectEntry(WelcomeScreen sc, String p) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        path = p;
        screen = sc;

        title = new JLabel(new File(p).getName());
        title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 20));
        desc = new JLabel(p);
        desc.setFont(new Font(desc.getFont().getFontName(), Font.ITALIC, 14));

        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(title);
        add(desc);

        setBackground(Color.getHSBColor(Utils.getHueByText(title.getText()), 0.5F, 0.5F));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBorder(new EmptyBorder(10, 15, 10, 10));
                revalidate();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBorder(new EmptyBorder(10, 10, 10, 10));
                revalidate();
            }

            public void mouseClicked(MouseEvent e) {
                Main.openedFrames.add(new EditorFrame(new File(path)));
                screen.dispose();
            }
        });
    }
}