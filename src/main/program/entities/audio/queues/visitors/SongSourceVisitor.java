package main.program.entities.audio.queues.visitors;

import main.program.entities.audio.collections.Album;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.files.Song;

/**
 * A visitor that visits song sources.
 */
public interface SongSourceVisitor {

    /**
     * Visit an album.
     */
    default void visit(final Album album) {
    }

    /**
     * Visit a playlist.
     */
    default void visit(final Playlist playlist) {
    }

    /**
     * Visit a song.
     */
    default void visit(final Song song) {
    }
}
