package main.entities.users.creators;

import java.util.Map;
import main.entities.audio.files.AudioFile;
import main.entities.users.User;
import main.entities.users.WrappedStats;

public interface CreatorWrapped<T extends AudioFile> extends WrappedStats {

    /**
     * Record one more appearance of `val` into `stat`.
     *
     * @param stat the frequency counter.
     * @param val  the value.
     * @param <U>  the type stored.
     */
    static <U> void increment(Map<U, Integer> stat, U val) {
        stat.put(val, stat.getOrDefault(val, 0) + 1);
    }


    /**
     * Record one listen from `listener` to the `listened` audio.
     *
     * @param listener the user that listened the audio.
     * @param listened the audio file.
     */
    void addListen(User listener, T listened);
}
