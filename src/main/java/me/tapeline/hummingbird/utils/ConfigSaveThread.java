package me.tapeline.hummingbird.utils;

import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.configuration.Configure;
import me.tapeline.hummingbird.windows.HFrame;

public class ConfigSaveThread extends Thread {

    public ConfigSaveThread() {
        this.start();
    }

    public void run() {
        while (true) {
            long start = System.currentTimeMillis();

            boolean stop = true;
            for (HFrame frame : Main.openedFrames) {
                if (frame.isVisible()) {
                    stop = false;
                    break;
                }
            }

            if (stop) {
                Configure.saveYaml(Main.configPath, Main.cfg);
                return;
            }

            while (start + 5000 > System.currentTimeMillis());
        }
    }

}
