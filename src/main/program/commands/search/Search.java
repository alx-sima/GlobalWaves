package main.program.commands.search;

import fileio.input.commands.SearchInput;
import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import fileio.output.SearchResultBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.entities.Searchable;
import main.program.Library;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;
import main.program.Player;
import main.program.Program;
import main.entities.users.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Search extends DependentCommand {

    private final SearchResultBuilder resultBuilder;
    private final String type;
    private final List<SearchFilter> filters;

    public Search(final SearchInput input) {
        super(input);
        resultBuilder = new SearchResultBuilder(this);
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
        Program program = Program.getInstance();
        Library library = program.getLibrary();

        return switch (searchType) {
            case "song" -> {
                Stream<Song> publicSongs = library.getSongs().stream();
                Stream<Song> albumSongs = library.getAlbums().stream()
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
            case "album" -> library.getAlbums().stream();
            case "artist" -> program.getDatabase().getArtists().stream();
            case "host" -> program.getDatabase().getHosts().stream();
            default -> Stream.empty();
        };
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency onlineUserDependency = new OnlineUserDependency(this, resultBuilder);
        return onlineUserDependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Program program = Program.getInstance();
        User caller = getCaller();

        Stream<? extends Searchable> searchPlace = getSearchPlace(caller, type);

        List<Searchable> valid = searchPlace.filter(this::itemMatchesFilters).limit(MAX_RESULTS)
            .collect(Collectors.toList());
        program.getSearchbar().setSearchResults(valid);

        List<String> result = valid.stream().map(Searchable::getName).toList();

        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        player.clearQueue();

        resultBuilder.withMessage("Search returned " + result.size() + " results");
        return resultBuilder.withResult(result);

    }
}
