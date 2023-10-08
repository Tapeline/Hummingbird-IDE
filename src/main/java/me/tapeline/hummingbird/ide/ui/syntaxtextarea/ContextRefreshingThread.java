package me.tapeline.hummingbird.ide.ui.syntaxtextarea;

import me.tapeline.hummingbird.ide.expansion.syntax.AbstractSyntaxAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ContextRefreshingThread extends Thread {

    public static long DEFAULT_TIMEOUT = 1500;

    private HSyntaxTextArea area;
    private KeyListener refresher;
    private boolean flag = false;
    private long timerStart;

    public ContextRefreshingThread(HSyntaxTextArea area) {
        this.area = area;
        refresher = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                flag = true;
            }
            @Override
            public void keyPressed(KeyEvent e) {
                flag = true;
            }
            @Override
            public void keyReleased(KeyEvent e) {
                flag = true;
            }
        };
    }

    public void run() {
        performContextUpdate();
        timerStart = System.currentTimeMillis();

        while (true) {
            long start = System.currentTimeMillis();

            if (flag) {
                flag = false;
                timerStart = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - timerStart >= DEFAULT_TIMEOUT){
                performContextUpdate();
                timerStart = System.currentTimeMillis();
            }

            while (System.currentTimeMillis() - start < 100) {}
        }
    }

    private void performContextUpdate() {
        area.context = area.context.deriveForText(area.getText());

        for (AbstractSyntaxAdapter adapter : area.applicableAdapters) {
            area.setTokenMaker(adapter.getTokenParser(area.context));
            break;
        }
    }

}
