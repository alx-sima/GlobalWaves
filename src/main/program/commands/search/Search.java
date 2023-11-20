package main.program.commands.search;

import fileio.input.SearchInput;
import fileio.output.CommandResult;
import fileio.output.SearchResult;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import main.audio.Searchable;
import main.audio.collections.Playlist;
import main.program.Player;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class Search extends Command {

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

    private Stream<? extends Searchable> getSearchPlace(final User callee,
        final String searchType) {
        Program instance = Program.getInstance();

        return switch (searchType) {
            case "song" -> instance.getLibrary().getSongs().stream();
            case "podcast" -> instance.getPodcasts().stream();
            case "playlist" -> {
                // Search in the user's playlists and also in the public playlists.
                Stream<Playlist> userPlaylists = callee.getPlaylists().stream();
                Stream<Playlist> publicPlaylists = instance.getPublicPlaylists().stream().sorted(
                    Comparator.comparingInt(Playlist::getCreationTimestamp));

                // Remove duplicates before searching.
                yield Stream.concat(userPlaylists, publicPlaylists).distinct();
            }
            default -> Stream.empty();
        };
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User callee = getCallee();

        Stream<? extends Searchable> searchPlace = getSearchPlace(callee, type);

        List<Searchable> valid = searchPlace.filter(this::itemMatchesFilters).limit(MAX_RESULTS)
            .collect(Collectors.toList());
        instance.getSearchbar().setSearchResults(valid);

        List<String> result = valid.stream().map(Searchable::getName).toList();
        Player player = callee.getPlayer();
        player.updateTime(timestamp);
        player.clearQueue();

        return new SearchResult(this, "Search returned " + result.size() + " results", result);
    }
}
