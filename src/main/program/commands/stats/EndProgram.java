package main.program.commands.stats;

import fileio.output.wrapped.WrappedOutput.Pair;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lombok.Getter;
import main.entities.audio.files.Song;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.entities.users.creators.Artist;
import main.entities.users.creators.content.Merch;

@Getter
public final class EndProgram {

    private final String command = "endProgram";

    private final Map<Pair, ArtistEndResult> result = new TreeMap<>(
        Comparator.comparingInt(Pair::getValue));

    public EndProgram(final int timestamp) {
        // Split everyone's remaining premium money.
        UserDatabase.getInstance().getUsers().stream().filter(User::isPremium)
            .forEach(u -> u.splitPremiumMoney(timestamp));

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

    private static final class ArtistEndResult {

        private static final double ROUNDING_EXPONENT = 1e2;

        @Getter
        private final double merchRevenue;
        @Getter
        private final double songRevenue;
        @Getter
        private final int ranking;
        @Getter
        private final String mostProfitableSong;

        ArtistEndResult(final Artist artist, final int ranking) {
            this.ranking = ranking;

            merchRevenue = roundDouble(
                artist.getMerch().stream().map(Merch::getTotalEarned).reduce(0.0d, Double::sum));

            List<Song> songs = artist.getAlbums().stream()
                .flatMap(album -> album.getSongs().stream()).toList();

            songRevenue = roundDouble(
                songs.stream().map(Song::getTotalEarned).reduce(0.0d, Double::sum));
            Song bestSong = songs.stream().max(Comparator.comparingDouble(Song::getTotalEarned)
                    .thenComparing((a, b) -> b.getName().compareTo(a.getName())))
                .orElse(null);
            if (bestSong != null && bestSong.getTotalEarned() > 0.0d) {
                mostProfitableSong = bestSong.getName();
            } else {
                mostProfitableSong = "N/A";
            }
        }

        /**
         * Round x to 2 decimal places.
         */
        private static double roundDouble(final double x) {
            return Math.round(x * ROUNDING_EXPONENT) / ROUNDING_EXPONENT;
        }
    }
}
