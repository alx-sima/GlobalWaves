package main.audio.collections;

import main.audio.files.AudioFile;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String user;
    private final boolean isPrivate = false;
    private final List<AudioFile> files = new ArrayList<>();

    public Playlist(final String user) {
        this.user = user;
    }
}
