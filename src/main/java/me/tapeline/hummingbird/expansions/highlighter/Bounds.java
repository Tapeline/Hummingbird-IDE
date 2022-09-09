package me.tapeline.hummingbird.expansions.highlighter;

public class Bounds {

    public int left;
    public int right;

    public Bounds(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public boolean in(int i) {
        return i >= left && i <= right;
    }

}
