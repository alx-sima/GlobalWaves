package main.audio.collections;

import main.audio.files.Song;

public final class SongVisitor implements PlayableVisitor {

    private Song currentSong = null;

    @Override
    public void visit(final Podcast podcast) {

    }

    @Override
    public void visit(final SongQueue queue) {
        currentSong = queue.getCurrentSong();
    }

    public Song getCurrentSong() {
        return currentSong;
    }
}
