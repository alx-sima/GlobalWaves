package main.entities.audio.queues.visitors;

import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;

public final class SongQueueVisitor {

    private final String owner;
    @Getter
    private boolean isOwned = false;

    public SongQueueVisitor(final String owner) {
        this.owner = owner;
    }

    public void visit(final Album album) {
        isOwned = owner.equals(album.getOwner());
    }

    public void visit(final Playlist playlist) {
        isOwned = owner.equals(playlist.getUser().getUsername());
    }

    public void visit(final Song song) {
        isOwned = owner.equals(song.getArtist());
    }
}
