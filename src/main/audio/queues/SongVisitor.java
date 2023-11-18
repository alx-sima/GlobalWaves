package main.audio.queues;

import lombok.Getter;
import main.audio.files.Song;

@Getter
public final class SongVisitor implements QueueVisitor {

    private Song currentSong = null;

    @Override
    public void visit(final SongQueue queue) {
        currentSong = queue.getCurrentSong();
    }
}
