package main.audio.collections;

import java.util.ArrayList;
import java.util.List;
import main.User;
import main.audio.Searchable;
import main.audio.files.AudioFile;

public final class Playlist implements Searchable {

    private final String name;
    private final User user;
    private final boolean isPrivate = false;
    private final List<AudioFile> songs = new ArrayList<>();

    public Playlist(final String name, final User user) {
        this.name = name;
        this.user = user;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<AudioFile> getContents() {
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

        for (AudioFile file : songs) {
            duration += file.getDuration();
            if (duration >= timePassed) {
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
}
