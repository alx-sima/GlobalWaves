package main.audio.queues;

import main.audio.files.Song;

public final class SongVisitor implements QueueVisitor {

    private Song currentSong = null;

    @Override
    public void visit(final SongQueue queue) {
        currentSong = queue.getCurrentSong();
    }

    public Song getCurrentSong() {
        return currentSong;
    }
}
