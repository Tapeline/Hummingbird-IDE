package me.tapeline.hummingbird.ide.utils;
public class Bounds {

    private final int start;
    private final int end;

    public Bounds(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

}
