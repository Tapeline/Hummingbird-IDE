package me.tapeline.hummingbird.ui.jcodeeditor;

import java.util.function.Consumer;

public class HighlightCooldown extends Thread {

    public static int cooldownM = 2000;
    public static boolean r;
    public static boolean allow = true;

    public void runIf(Consumer<Boolean> consumer) {
        if (allow) consumer.accept(true);
        allow = false;
    }

    public static void reset() {
        r = true;
    }

    public HighlightCooldown() {
        start();
    }

    public void run() {
        while (true) {
            long s = System.currentTimeMillis();
            while (!r || System.currentTimeMillis() - 2000 > s);
            if (r) {
                r = false;
            } else {
                allow = true;
            }
        }
    }

}
