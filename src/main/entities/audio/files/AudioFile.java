package main.entities.audio.files;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import main.entities.users.User;
import main.entities.users.creators.Artist;
import main.entities.users.creators.CreatorWrapped;
import main.entities.users.creators.Host;
import main.program.InvalidOperation;

/**
 * An audio file, which can be part of a play queue.
 */
@Getter
public abstract class AudioFile {

    protected final String name;
    private final int duration;
    private final String owner;
    private final Map<User, Integer> listeners = new HashMap<>();

    protected AudioFile(final String name, final int duration, final String owner) {
        this.name = name;
        this.duration = duration;
        this.owner = owner;
    }

    public  Host getHost() throws InvalidOperation {
        throw new InvalidOperation();
    }

    public Artist getArtist() throws InvalidOperation {
        throw new InvalidOperation();
    }

    /**
     * Record one listen for the listener.
     *
     * @param listener the user that listened the file.
     */
    public void addListen(final User listener) {
        CreatorWrapped.increment(listeners, listener);
    }

    /**
     * Get the total number of listens of this file.
     */
    public int getNumberOfListens() {
        return listeners.values().stream().reduce(0, Integer::sum);
    }
}
