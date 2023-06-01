package me.tapeline.carousellib.utils;

public class Flags {

    public static boolean hasFlag(int flags, int flag) {
        return (flags & flag) == flag;
    }

}
