package main.audio.collections;

import main.User;
import main.audio.files.AudioFile;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private final User user;
    private final boolean isPrivate = false;
    private final List<AudioFile> files = new ArrayList<>();

    public Playlist(final User user) {
        this.user = user;
    }
}
