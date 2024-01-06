package fileio.output;

import java.util.List;
import lombok.Getter;
import main.program.entities.audio.collections.Album;
import main.program.entities.audio.files.Song;
import main.program.commands.Command;

@Getter
public final class AlbumResult extends MessageResult {

    private final List<AlbumOutput> result;

    private AlbumResult(final Builder builder) {
        super(builder);
        this.result = builder.result;
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

    @Getter
    public static final class Builder extends ResultBuilder {

        private List<AlbumOutput> result;

        public Builder(final Command cmd) {
            super(cmd);
        }

        /**
         * Set the result.
         */
        public Builder result(final List<Album> albums) {
            result = albums.stream().map(AlbumOutput::new).toList();
            return this;
        }

        @Override
        public MessageResult build() {
            return new AlbumResult(this);
        }
    }
}

