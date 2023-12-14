package main.program.commands.search;

import fileio.input.commands.SearchInput;
import fileio.output.builders.ResultBuilder;
import fileio.output.builders.SearchResultBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import main.entities.Searchable;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.program.Library;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

public final class Search extends OnlineUserCommand {

    @Getter
    private final SearchResultBuilder resultBuilder = new SearchResultBuilder().withCommand(this);
    private final String type;
    private final List<SearchFilter> filters;

    public Search(final SearchInput input) {
        super(input);
        type = input.getType();
        filters = input.createFilters();
    }

    private boolean itemMatchesFilters(final Searchable item) {
        for (SearchFilter filter : filters) {
            if (!filter.matchItem(item)) {
                return false;
            }
        }

        return true;
    }

    private Stream<? extends Searchable> getSearchPlace(final User caller,
        final String searchType) {
        Library library = Library.getInstance();
        UserDatabase database = UserDatabase.getInstance();

        return switch (searchType) {
            case "song" -> {
                Stream<Song> publicSongs = library.getSongs().stream();
                Stream<Song> albumSongs = database.getAlbums().stream()
                    .flatMap(album -> album.getSongs().stream());

                yield Stream.concat(publicSongs, albumSongs);
            }
            case "podcast" -> library.getPodcasts().stream();
            case "playlist" -> {
                // Search in the user's playlists and also in the public playlists.
                Stream<Playlist> userPlaylists = caller.getPlaylists().stream();
                Stream<Playlist> publicPlaylists = library.getPublicPlaylists().stream()
                    .sorted(Comparator.comparingInt(Playlist::getCreationTimestamp));

                // Remove duplicates before searching.
                yield Stream.concat(userPlaylists, publicPlaylists).distinct();
            }
            case "album" -> database.getAlbums().stream();
            case "artist" -> database.getArtists().stream();
            case "host" -> database.getHosts().stream();
            default -> Stream.empty();
        };
    }

    @Override
    protected ResultBuilder execute(final User caller) {

        Stream<? extends Searchable> searchPlace = getSearchPlace(caller, type);

        List<Searchable> valid = searchPlace.filter(this::itemMatchesFilters).limit(MAX_RESULTS)
            .collect(Collectors.toList());
        caller.getSearchbar().setSearchResults(valid);

        List<String> result = valid.stream().map(Searchable::getName).toList();

        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        player.clearQueue();

        resultBuilder.withMessage("Search returned " + result.size() + " results");
        return resultBuilder.withResult(result);
    }
}
