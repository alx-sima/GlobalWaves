package fileio.output.wrapped;

import java.util.Map;
import lombok.Getter;
import main.entities.users.User;

@Getter
public final class UserWrapped implements WrappedOutput {

    private final Map<Pair, Integer> topArtists;
    private final Map<Pair, Integer> topGenres;
    private final Map<Pair, Integer> topSongs;
    private final Map<Pair, Integer> topAlbums;
    private final Map<Pair, Integer> topEpisodes;


    public UserWrapped(final User.Wrapped wrapped) {
        topArtists = WrappedOutput.getTop(wrapped.getTopArtists());
        topGenres = WrappedOutput.getTop(wrapped.getTopGenres());
        topSongs = WrappedOutput.getTop(wrapped.getTopSongs());
        topAlbums = WrappedOutput.getTop(wrapped.getTopAlbums());
        topEpisodes = WrappedOutput.getTop(wrapped.getTopEpisodes());
    }

    @Override
    public String returnMessage() {
        if (topArtists.isEmpty() && topGenres.isEmpty() && topSongs.isEmpty()
            && topAlbums.isEmpty() && topEpisodes.isEmpty()) {
            return "No data to show for user %s.";
        }

        return null;
    }
}
