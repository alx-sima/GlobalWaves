package fileio.output.wrapped;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;
import lombok.Getter;

public interface WrappedOutput {

    /**
     * Get the top 5 entries from the `stat` category.
     *
     * @param stat the map containing the entries.
     * @param <T>  the entries' type.
     * @return a new map with the top entries.
     */
    static <T> Map<Pair, Integer> getTop(Map<T, Integer> stat) {

        Comparator<Pair> comparator = Comparator.comparingInt(Pair::getValue).reversed()
            .thenComparing(Pair::getKey);

        Stream<Pair> top = stat.entrySet().stream().map(Pair::new).sorted(comparator).limit(5);

        Map<Pair, Integer> topSet = new TreeMap<>(comparator);
        top.forEach(pair -> topSet.put(pair, pair.getValue()));
        return topSet;
    }

    /**
     * Check if the wrapped stats are empty and return an error message, or *null* otherwise.
     *
     * @return an error message format, taking the user's name as a parameter (String).
     */
    String returnMessage();

    @Getter
    final class Pair {

        private final String key;
        private final int value;

        public Pair(final Object key, final int value) {
            this.key = key.toString();
            this.value = value;
        }

        public Pair(final Entry<?, Integer> mapEntry) {
            key = mapEntry.getKey().toString();
            value = mapEntry.getValue();
        }

        @Override
        public String toString() {
            return key;
        }
    }
}
