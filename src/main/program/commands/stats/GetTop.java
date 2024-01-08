package main.program.commands.stats;

import static main.program.Program.MAX_RESULTS;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import fileio.output.StatsResult.Builder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.Getter;
import main.program.commands.Command;
import main.program.databases.Library;
import main.program.databases.UserDatabase;
import main.program.entities.Searchable;
import main.program.entities.audio.collections.Album;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.files.Song;
import main.program.entities.users.creators.Artist;

@Getter
public final class GetTop extends Command {

    private final StatsResult.Builder resultBuilder = new Builder(this);

    public GetTop(final CommandInput input) {
        super(input);
    }

    private static <T extends Searchable> Stream<T> getTop(final Stream<T> stream,
        final Comparator<T> topCriterion) {
        return stream.sorted(topCriterion).limit(MAX_RESULTS);
    }


    private static Stream<Album> getTopAlbums() {
        Stream<Album> albums = UserDatabase.getInstance().getAlbums().stream();

        Comparator<Album> comparator = Comparator.comparingInt(Album::getLikes).reversed()
            .thenComparing(Album::getName);

        return getTop(albums, comparator);
    }

    private static Stream<Artist> getTopArtists() {
        Stream<Artist> artists = UserDatabase.getInstance().getArtists().stream();

        Comparator<Artist> comparator = Comparator.comparingInt(Artist::getLikes).reversed()
            .thenComparing(Artist::getName);

        return getTop(artists, comparator);
    }

    private static Stream<Playlist> getTopPlaylists() {
        Stream<Playlist> playlists = Library.getInstance().getPublicPlaylists().stream();

        // Compare first by number of followers, then by age (timestamp of creation).
        Comparator<Playlist> comparator = Comparator.comparingInt(Playlist::getFollowers).reversed()
            .thenComparingInt(Playlist::getCreationTimestamp);

        return getTop(playlists, comparator);
    }


    private static Stream<Song> getTopSongs() {
        Stream<Song> songs = UserDatabase.getInstance().getArtists().stream()
            .flatMap(Artist::getAllSongs);

        // Compare by number of likes in descending order.
        Comparator<Song> comparator = Comparator.comparingInt(Song::getLikes).reversed()
            .thenComparingInt(Song::getCreationTime);

        return getTop(songs, comparator);
    }

    @Override
    public CommandResult execute() {
        Stream<? extends Searchable> top = switch (command) {
            case "getTop5Albums" -> getTopAlbums();
            case "getTop5Artists" -> getTopArtists();
            case "getTop5Playlists" -> getTopPlaylists();
            case "getTop5Songs" -> getTopSongs();
            default -> Stream.empty();
        };

        List<String> topNames = top.map(Searchable::getName).toList();
        return resultBuilder.result(topNames).build();
    }
}
