package me.tapeline.hummingbird.ide.frames;

public class DialogResult<T> {

    public enum Type { OK, CANCEL }

    protected final Type type;
    protected final T value;

    public DialogResult(Type type, T value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

}
