package main.entities.audio.queues.visitors;

import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;
import main.entities.audio.queues.PodcastQueue;
import main.entities.audio.queues.SongQueue;

/**
 * A visitor that checks if a queue is playing files owned by a certain user.
 */
public final class OwnerVisitor implements QueueVisitor {

    private final String owner;
    @Getter
    private boolean isOwned = false;

    public OwnerVisitor(final String owner) {
        this.owner = owner;
    }

    @Override
    public void visit(final SongQueue queue) {
        SongOwnerVisitor visitor = new SongOwnerVisitor(owner);
        queue.getSongSource().accept(visitor);
        isOwned = visitor.isOwned();
    }

    @Override
    public void visit(final PodcastQueue podcast) {
        isOwned = owner.equals(podcast.getCurrentlyPlaying().getOwner());
    }

    private static final class SongOwnerVisitor implements SongSourceVisitor {

        private final String owner;
        @Getter
        private boolean isOwned = false;

        private SongOwnerVisitor(final String owner) {
            this.owner = owner;
        }

        @Override
        public void visit(final Album album) {
            isOwned = owner.equals(album.getOwner().getUsername());
        }

        @Override
        public void visit(final Playlist playlist) {
            isOwned = owner.equals(playlist.getUser().getUsername());
        }

        @Override
        public void visit(final Song song) {
            isOwned = owner.equals(song.getArtist().getUsername());
        }
    }
}
