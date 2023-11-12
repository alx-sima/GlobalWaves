package main.audio;

import main.audio.files.AudioFile;
import main.audio.files.Song;

import java.util.ArrayList;
import java.util.List;

public final class Player {

    private List<AudioFile> queue = new ArrayList<>();

    public Player() {
    }

    public List<AudioFile> getQueue() {
        return queue;
    }

    public void setQueue(List<AudioFile> queue) {
        this.queue = queue;
    }
}
