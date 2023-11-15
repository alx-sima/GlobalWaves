package main.audio;

import main.audio.collections.Playlist;
import main.audio.collections.Podcast;
import main.audio.files.Song;

public interface SearchableVisitor {

    void visit(Playlist playlist);

    void visit(Podcast podcast);

    void visit(Song song);
}
