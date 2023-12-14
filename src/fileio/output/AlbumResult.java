package fileio.output;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.entities.audio.collections.Album;
import main.entities.audio.files.Song;

@Getter
@Setter
public final class AlbumResult extends CommandResult {

    private List<AlbumOutput> result;

    public void setResult(final List<Album> result) {
        this.result = result.stream().map(AlbumOutput::new).toList();
    }

    @Getter
    private static final class AlbumOutput {

        private final String name;
        private final List<String> songs;

        private AlbumOutput(final Album album) {
            name = album.getName();
            songs = album.getSongs().stream().map(Song::getName).toList();
        }
    }
}

