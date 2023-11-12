package main.audio.collections;

import main.User;
import main.audio.Searchable;
import main.audio.files.AudioFile;

import java.util.ArrayList;
import java.util.List;

public class Playlist implements Searchable {
    private final User user;
    private final boolean isPrivate = false;
    private final List<AudioFile> files = new ArrayList<>();

    public Playlist(final User user) {
        this.user = user;
    }

    @Override
    public boolean matchFilter(String filter, String parameter) {
        return false;
    }
}
