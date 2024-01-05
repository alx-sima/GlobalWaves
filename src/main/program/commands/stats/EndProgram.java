package main.program.commands.stats;

import fileio.output.wrapped.WrappedOutput.Pair;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import main.entities.audio.files.Song;
import main.entities.users.UserDatabase;
import main.entities.users.artist.Artist;
import main.entities.users.artist.Merch;

@Getter
public final class EndProgram {

    private final String command = "endProgram";

    private final Map<Pair, ArtistEndResult> result = new TreeMap<>(
        Comparator.comparingInt(Pair::getValue));

    public EndProgram() {
        List<Artist> orderedArtists = UserDatabase.getInstance().getMonetizedArtists().stream()
            .sorted(Comparator.<Artist>comparingDouble(artist ->
                    artist.getMerch().stream().map(Merch::getTotalEarned).reduce(0.0d, Double::sum)
                        + artist.getAlbums().stream().flatMap(album -> album.getSongs().stream())
                        .map(Song::getTotalEarned).reduce(0.0d, Double::sum)).reversed()
                .thenComparing(Artist::getName)).toList();

        for (int i = 0; i < orderedArtists.size(); i++) {
            Artist artist = orderedArtists.get(i);
            result.put(new Pair(artist, i), new ArtistEndResult(artist, i + 1));
        }
    }

    @Getter
    private static final class ArtistEndResult {

        private final double merchRevenue;
        private final double songRevenue;
        private final int ranking;
        private final String mostProfitableSong;

        ArtistEndResult(final Artist artist, final int ranking) {
            this.ranking = ranking;

            merchRevenue = artist.getMerch().stream().map(Merch::getTotalEarned)
                .reduce(0.0d, Double::sum);

            List<Song> songs = artist.getAlbums().stream()
                .flatMap(album -> album.getSongs().stream()).toList();

            songRevenue = songs.stream().map(Song::getTotalEarned).reduce(0.0d, Double::sum);
            Song bestSong = songs.stream().max(Comparator.comparingDouble(Song::getTotalEarned))
                .orElse(null);
            if (bestSong != null && bestSong.getTotalEarned() > 0.0d) {
                mostProfitableSong = bestSong.getName();
            } else {
                mostProfitableSong = "N/A";
            }
        }
    }
}
