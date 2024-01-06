package main.program.entities.users.interactions.wrapped;

import java.util.Map;

public interface CreatorWrapped extends WrappedStats {

    /**
     * Record one more appearance of `val` into `stat`.
     *
     * @param stat the frequency counter.
     * @param val  the value.
     * @param <T>  the type stored.
     */
    static <T> void increment(Map<T, Integer> stat, T val) {
        add(stat, val, 1);
    }

    static <T> void add(Map<T, Integer> stat, T val, int occurrences) {
        stat.put(val, stat.getOrDefault(val, 0) + occurrences);
    }
}
