package fileio.output.wrapped;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import main.program.Program;
import main.program.entities.users.User;
import main.program.entities.users.creators.Artist;

@Getter
public final class ArtistWrapped implements WrappedOutput {

    private final Map<Pair, Integer> topAlbums;
    private final Map<Pair, Integer> topSongs;
    private final List<String> topFans;
    private final int listeners;

    public ArtistWrapped(final Artist artist) {
        Artist.Stats stats = artist.getStats();

        Comparator<Entry<User, Integer>> comparator = Entry.<User, Integer>comparingByValue()
            .reversed()
            .thenComparing(Entry.comparingByKey(Comparator.comparing(User::getUsername)));

        topAlbums = WrappedOutput.getTop(stats.getAlbumListens());
        topSongs = WrappedOutput.getTop(stats.getSongListens());
        topFans = stats.getListensByUser().entrySet().stream().sorted(comparator)
            .limit(Program.MAX_RESULTS).map(e -> e.getKey().getUsername()).toList();
        listeners = stats.getListensByUser().size();
    }

    @Override
    public String returnMessage() {
        if (topAlbums.isEmpty() && topSongs.isEmpty() && topFans.isEmpty()) {
            return "No data to show for artist %s.";
        }

        return null;
    }
}
