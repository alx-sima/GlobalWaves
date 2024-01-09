package main.program.commands.stats;

import fileio.output.wrapped.WrappedOutput.Pair;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import lombok.Getter;
import main.program.databases.UserDatabase;
import main.program.entities.audio.files.Song;
import main.program.entities.users.User;
import main.program.entities.users.creators.Artist;
import main.program.entities.users.creators.content.Merch;

@Getter
public final class EndProgram {

    private final String command = "endProgram";

    private final Map<Pair, ArtistEndResult> result = new TreeMap<>(
        Comparator.comparingInt(Pair::getValue));

    public EndProgram(final int timestamp) {
        // Split everyone's remaining premium money.
        UserDatabase.getInstance().getUsers().stream().filter(User::isPremium).forEach(u -> {
            u.getPlayer().updateTime(timestamp);
            u.splitPremiumMoney();
        });

        List<Artist> orderedArtists = UserDatabase.getInstance().getMonetizedArtists().stream()
            .sorted(Comparator.comparingDouble(Artist::getTotalRevenue).reversed()
                .thenComparing(Artist::getName)).toList();

        for (int i = 0; i < orderedArtists.size(); i++) {
            Artist artist = orderedArtists.get(i);
            result.put(new Pair(artist, i), new ArtistEndResult(artist, i + 1));
        }
    }

    @Getter
    private static final class ArtistEndResult {

        private static final double ROUNDING_EXPONENT = 1e2;

        private final double merchRevenue;
        private final double songRevenue;
        private final int ranking;
        private final String mostProfitableSong;

        ArtistEndResult(final Artist artist, final int ranking) {
            this.ranking = ranking;

            merchRevenue = roundDouble(
                artist.getMerch().stream().map(Merch::getTotalEarned).reduce(0.0d, Double::sum));

            List<Song> songs = artist.getAlbums().stream()
                .flatMap(album -> album.getSongs().stream()).toList();

            songRevenue = roundDouble(
                songs.stream().map(Song::getTotalEarned).reduce(0.0d, Double::sum));

            String bestSong = getBestSong(artist);
            mostProfitableSong = Objects.requireNonNullElse(bestSong, "N/A");
        }

        private static String getBestSong(final Artist artist) {
            Map<String, Double> songRevenue = new HashMap<>();

            artist.getAllSongs().forEach(
                song -> songRevenue.merge(song.getName(), song.getTotalEarned(), Double::sum));

            Entry<String, Double> bestSongEntry = songRevenue.entrySet().stream().max(
                Entry.<String, Double>comparingByValue()
                    .thenComparing(Entry.<String, Double>comparingByKey().reversed())).orElse(null);
            if (bestSongEntry == null || bestSongEntry.getValue() <= 0.0d) {
                return null;
            }

            return bestSongEntry.getKey();
        }

        /**
         * Round `x` to 2 decimal places.
         */
        private static double roundDouble(final double x) {
            return Math.round(x * ROUNDING_EXPONENT) / ROUNDING_EXPONENT;
        }
    }
}
