package main.audio.collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import main.User;
import main.audio.Searchable;
import main.audio.SearchableVisitor;
import main.audio.files.AudioFile;
import main.audio.files.Song;

public final class Playlist implements Searchable {

    private final String name;
    private final User user;
    private final boolean isPrivate = false;
    private final List<Song> songs = new ArrayList<>();
    private int currentSongIndex = 0;
    private int followers = 0;

    public Playlist(final String name, final User user) {
        this.name = name;
        this.user = user;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public int getFollowers() {
        return followers;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Song> getContents() {
        return songs;
    }

    @Override
    public boolean matchFilter(final String filter, final String parameter) {
        return switch (filter) {
            case "name" -> name.startsWith(parameter);
            case "owner" -> user.getUsername().equals(parameter);
            default -> false;
        };
    }

    @Override
    public AudioFile getSongAt(final int timePassed) {
        int duration = 0;

        for (int i = 0; i < songs.size(); i++) {
            AudioFile file = songs.get(i);

            duration += file.getDuration();
            if (duration >= timePassed) {
                currentSongIndex = i;
                return file;
            }
        }

        return null;
    }

    @Override
    public RepeatMode nextRepeatMode(final RepeatMode mode) {
        return switch (mode) {
            case NO_REPEAT -> RepeatMode.REPEAT_ALL;
            case REPEAT_ALL -> RepeatMode.REPEAT_CURRENT;
            case REPEAT_CURRENT -> RepeatMode.NO_REPEAT;
            default -> null;
        };
    }

    public void accept(SearchableVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Add the song to the playlist, or remove it if it was already present.
     *
     * @param song The song to be added (or removed).
     * @return true if the song was added after this operation.
     */
    public boolean addRemoveSong(Song song) {
        if (songs.remove(song)) {
            return false;
        }

        songs.add(song);
        return true;
    }

    @JsonIgnore
    public Song getCurrentSong() {
        if (currentSongIndex >= songs.size()) {
            return null;
        }

        return songs.get(currentSongIndex);
    }
}
