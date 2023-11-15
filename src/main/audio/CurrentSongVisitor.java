package main.audio;

import main.audio.collections.Playlist;
import main.audio.collections.Podcast;
import main.audio.files.Song;

public final class CurrentSongVisitor implements SearchableVisitor {

    private Song playingSong = null;

    @Override
    public void visit(Playlist playlist) {
        playingSong = playlist.getCurrentSong();
    }

    @Override
    public void visit(Podcast podcast) {

    }

    @Override
    public void visit(Song song) {
        playingSong = song;
    }

    public Song getPlayingSong() {
        return playingSong;
    }
}
