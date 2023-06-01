package me.tapeline.hummingbird.ide.ui.studiopanel;

public class WorkspaceDividerPositions {

    private double topBottomPercent;
    private double horizontalLeftPercent;
    private double horizontalRightPercent;
    private boolean bottomHidden;
    private boolean leftHidden;
    private boolean rightHidden;

    public WorkspaceDividerPositions(double topBottomPercent,
                                     double horizontalLeftPercent,
                                     double horizontalRightPercent,
                                     boolean bottomHidden,
                                     boolean leftHidden,
                                     boolean rightHidden) {
        this.topBottomPercent = topBottomPercent;
        this.horizontalLeftPercent = horizontalLeftPercent;
        this.horizontalRightPercent = horizontalRightPercent;
        this.bottomHidden = bottomHidden;
        this.leftHidden = leftHidden;
        this.rightHidden = rightHidden;
    }

    public WorkspaceDividerPositions() {
        this(0.5, 0.5, 0.5,
                false, false, false);
    }

    public double getTopBottomPercent() {
        return topBottomPercent;
    }

    public void setTopBottomPercent(double topBottomPercent) {
        this.topBottomPercent = topBottomPercent;
    }

    public double getHorizontalLeftPercent() {
        return horizontalLeftPercent;
    }

    public void setHorizontalLeftPercent(double horizontalLeftPercent) {
        this.horizontalLeftPercent = horizontalLeftPercent;
    }

    public boolean isBottomHidden() {
        return bottomHidden;
    }

    public void setBottomHidden(boolean bottomHidden) {
        this.bottomHidden = bottomHidden;
    }

    public boolean isLeftHidden() {
        return leftHidden;
    }

    public void setLeftHidden(boolean leftHidden) {
        this.leftHidden = leftHidden;
    }

    public double getHorizontalRightPercent() {
        return horizontalRightPercent;
    }

    public void setHorizontalRightPercent(double horizontalRightPercent) {
        this.horizontalRightPercent = horizontalRightPercent;
    }

    public boolean isRightHidden() {
        return rightHidden;
    }

    public void setRightHidden(boolean rightHidden) {
        this.rightHidden = rightHidden;
    }

}
