package fileio.output.wrapped;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Getter;
import main.entities.audio.files.Song;
import main.entities.users.User;
import main.entities.users.creators.Artist;
import main.entities.users.creators.CreatorWrapped;
import main.program.Program;

@Getter
public final class ArtistWrapped implements WrappedOutput {

    private final Map<Pair, Integer> topAlbums;
    private final Map<Pair, Integer> topSongs;
    private final List<String> topFans;
    private final int listeners;

    public ArtistWrapped(final Artist artist) {
        Iterator<Song> songs = artist.getAlbums().stream()
            .flatMap(album -> album.getSongs().stream()).iterator();

        Map<String, Integer> albumsMap = new HashMap<>();
        Map<String, Integer> songsMap = new HashMap<>();
        Map<String, Integer> fansMap = new HashMap<>();

        while (songs.hasNext()) {
            Song song = songs.next();

            int totalListens = song.getListeners().values().stream().reduce(0, Integer::sum);
            if (totalListens == 0) {
                continue;
            }

            CreatorWrapped.add(albumsMap, song.getAlbum().getName(), totalListens);
            CreatorWrapped.add(songsMap, song.getName(), totalListens);

            for (Entry<User, Integer> entry : song.getListeners().entrySet()) {
                CreatorWrapped.add(fansMap, entry.getKey().toString(), entry.getValue());
            }
        }

        Comparator<Entry<String, Integer>> comparator = Entry.<String, Integer>comparingByValue()
            .reversed().thenComparing(Entry.comparingByKey());

        topAlbums = WrappedOutput.getTop(albumsMap);
        topSongs = WrappedOutput.getTop(songsMap);
        topFans = fansMap.entrySet().stream().sorted(comparator)
            .limit(Program.MAX_RESULTS)
            .map(Entry::getKey).toList();
        listeners = fansMap.size();
    }

    @Override
    public String returnMessage() {
        if (topAlbums.isEmpty() && topSongs.isEmpty() && topFans.isEmpty()) {
            return "No data to show for artist %s.";
        }

        return null;
    }
}
