package fileio.output;

import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Album;
import main.entities.audio.files.Song;

@Getter
public final class AlbumOutput {

    private final String name;
    private final List<String> songs;

    public AlbumOutput(final Album album) {
        name = album.getName();
        songs = album.getSongs().stream().map(Song::getName).toList();
    }
}
