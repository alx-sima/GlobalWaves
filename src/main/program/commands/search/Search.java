package main.program.commands.search;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import java.util.Comparator;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.User;
import main.program.Player;
import main.audio.Searchable;
import main.program.commands.Command;
import fileio.output.SearchResult;
import main.program.commands.search.filters.ComplexFilter;
import main.program.commands.search.filters.Filter;
import main.program.commands.search.filters.SimpleFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Search extends Command {

    private final String type;
    private final List<Filter> filters = new ArrayList<>();

    public Search(final CommandInput input) {
        super(input);
        type = input.getType();

        // TODO: refactor later
        if (input.getFilters().getName() != null) {
            filters.add(new SimpleFilter("name", input.getFilters().getName()));
        }
        if (input.getFilters().getAlbum() != null) {
            filters.add(new SimpleFilter("album", input.getFilters().getAlbum()));
        }
        if (input.getFilters().getTags() != null) {
            filters.add(new ComplexFilter("tags", input.getFilters().getTags()));
        }
        if (input.getFilters().getLyrics() != null) {
            filters.add(new SimpleFilter("lyrics", input.getFilters().getLyrics()));
        }
        if (input.getFilters().getGenre() != null) {
            filters.add(new SimpleFilter("genre", input.getFilters().getGenre()));
        }
        if (input.getFilters().getReleaseYear() != null) {
            filters.add(new SimpleFilter("releaseYear", input.getFilters().getReleaseYear()));
        }
        if (input.getFilters().getArtist() != null) {
            filters.add(new SimpleFilter("artist", input.getFilters().getArtist()));
        }
        if (input.getFilters().getOwner() != null) {
            filters.add(new SimpleFilter("owner", input.getFilters().getOwner()));
        }
    }

    private boolean itemMatchesFilters(final Searchable item) {
        for (Filter filter : filters) {
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
                Stream<Playlist> userPlaylists = callee.getPlaylists().stream();
                Stream<Playlist> publicPlaylists = instance.getPublicPlaylists().stream().sorted(
                    Comparator.comparingInt(Playlist::getCreationTimestamp));
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
        instance.setSearchResults(valid);

        List<String> result = valid.stream().map(Searchable::getName).toList();
        Player player = callee.getPlayer();
        player.updateTime(timestamp);
        player.clearQueue();

        return new SearchResult(this, "Search returned " + result.size() + " results", result);
    }
}
