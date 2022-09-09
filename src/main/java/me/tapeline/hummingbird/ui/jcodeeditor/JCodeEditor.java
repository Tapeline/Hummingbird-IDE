package me.tapeline.hummingbird.ui.jcodeeditor;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;
import me.tapeline.hummingbird.ui.EditorTab;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class JCodeEditor extends JTextPane {

    public EditorTab tab;
    public List<SyntaxTip> tipList = new ArrayList<>();
    public Timer timer;
    public TimerTask tooltipTask;

    public JCodeEditor(EditorTab t) {
        super();
        JCodeEditor that = this;

        tooltipTask = new TimerTask() {
            @Override
            public void run() {
                int pos = viewToModel(that.getMousePosition(true));
                boolean isSet = false;
                for (SyntaxTip tip : tipList) {
                    if (tip.bounds.in(pos)) {
                        that.setToolTipText("<b>" + tip.tip + "</b>");
                        that.tab.frame.console.setText(that.tab.frame.console.getText() + tip.tip + "\n");
                        return;
                    }
                }
            }
        };

        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(tooltipTask, 0, 1500);

        tab = t;
        setBackground(Registry.currentTheme.colors().backgroundText);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                HighlightingThread th = new HighlightingThread(that);
                th.start();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                timer.cancel();
                timer.purge();
                timer.scheduleAtFixedRate(tooltipTask, 0, 1500);
                super.mouseMoved(e);
            }
        });
    }

    public String getVisibleText() {
        JViewport viewport = tab.scrollPane.getViewport();
        Point startPoint = viewport.getViewPosition();
        Dimension size = viewport.getExtentSize();
        Point endPoint = new Point(startPoint.x + size.width, startPoint.y + size.height);
        int start = viewToModel( startPoint );
        int end = viewToModel( endPoint );
        try {
            return getText(start, end - start);
        } catch (BadLocationException e) {
            return getText();
        }
    }

}
