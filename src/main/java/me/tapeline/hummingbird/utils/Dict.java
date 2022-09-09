package me.tapeline.hummingbird.utils;

import java.util.HashMap;
import java.util.Map;

public class Dict {

    public static class Pair<A, B> {
        public A key;
        public B value;

        public Pair(A key, B value) {
            this.key = key;
            this.value = value;
        }
    }

    @SafeVarargs
    public static <X, Y> Map<X, Y> map(Pair<X, Y>... pairs) {
        Map<X, Y> map = new HashMap<>();
        for (Pair<X, Y> pair : pairs) {
            map.put(pair.key, pair.value);
        }
        return map;
    }

    public static <C, D> Pair<C, D> pair(C a, D b) {
        return new Pair<>(a, b);
    }

}
