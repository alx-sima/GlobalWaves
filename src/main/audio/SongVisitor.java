package main.audio;

import main.audio.collections.Playlist;
import main.audio.collections.Podcast;
import main.audio.files.Song;

public final class SongVisitor implements SearchableVisitor {

    private Song containedSong = null;

    @Override
    public void visit(Playlist playlist) {

    }

    @Override
    public void visit(Podcast podcast) {

    }

    @Override
    public void visit(Song song) {
        this.containedSong = song;
    }

    public Song getContainedSong() {
        return containedSong;
    }
}
