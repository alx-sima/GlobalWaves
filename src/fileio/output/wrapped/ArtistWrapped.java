package fileio.output.wrapped;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import main.program.entities.users.creators.Artist;
import main.program.entities.users.creators.Artist.Stats;
import main.program.Program;

@Getter
public final class ArtistWrapped implements WrappedOutput {

    private final Map<Pair, Integer> topAlbums;
    private final Map<Pair, Integer> topSongs;
    private final List<String> topFans;
    private final int listeners;

    public ArtistWrapped(final Artist artist) {
        Artist.Stats stats = new Stats(artist);

        Comparator<Entry<String, Integer>> comparator = Entry.<String, Integer>comparingByValue()
            .reversed().thenComparing(Entry.comparingByKey());
        topAlbums = WrappedOutput.getTop(stats.getAlbumsMap());
        topSongs = WrappedOutput.getTop(stats.getSongsMap());
        topFans = stats.getFansMap().entrySet().stream().sorted(comparator)
            .limit(Program.MAX_RESULTS)
            .map(Entry::getKey).toList();
        listeners = stats.getFansMap().size();
    }

    @Override
    public String returnMessage() {
        if (topAlbums.isEmpty() && topSongs.isEmpty() && topFans.isEmpty()) {
            return "No data to show for artist %s.";
        }

        return null;
    }
}
