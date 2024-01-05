package fileio.output;

import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Playlist;
import main.program.commands.Command;

@Getter
public final class PlaylistResult extends MessageResult {

    private final List<PlaylistOutput> result;

    private PlaylistResult(final Builder builder) {
        super(builder);
        this.result = builder.result;
    }


    @Getter
    private static final class PlaylistOutput {

        private final String name;
        private final List<String> songs;
        private final String visibility;
        private final int followers;

        private PlaylistOutput(final Playlist playlist) {
            name = playlist.getName();
            songs = playlist.getSongNames();
            visibility = playlist.isPrivate() ? "private" : "public";
            followers = playlist.getFollowers();
        }
    }

    public static final class Builder extends ResultBuilder {

        private List<PlaylistOutput> result;

        public Builder(final Command cmd) {
            super(cmd);
        }

        /**
         * Set the result.
         */
        public Builder result(final List<Playlist> playlists) {
            result = playlists.stream().map(PlaylistOutput::new).toList();
            return this;
        }

        @Override
        public MessageResult build() {
            return new PlaylistResult(this);
        }
    }
}
