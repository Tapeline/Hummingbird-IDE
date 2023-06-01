package me.tapeline.carousellib.data;

import java.util.HashMap;

public class Dict {

    @SafeVarargs
    public static <K, V> HashMap<K, V> make(Pair<K, V>... pairs) {
        HashMap<K, V> map = new HashMap<>();
        for (Pair<K, V> pair : pairs)
            map.put(pair.getA(), pair.getB());
        return map;
    }

}
