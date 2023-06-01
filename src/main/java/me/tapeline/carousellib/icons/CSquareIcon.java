package me.tapeline.carousellib.icons;

public abstract class CSquareIcon extends CBundledIcon {

    private int size;

    public CSquareIcon() {
        this(CBundledIcon.DEFAULT_HEIGHT);
    }

    public CSquareIcon(int size) {
        super(size, size);
        this.size = size;
    }

    public int getIconSize() {
        return size;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}
