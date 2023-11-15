package main.commands.search;

import fileio.input.CommandInput;
import main.Program;
import main.User;
import main.audio.Searchable;
import main.commands.Command;
import main.commands.Result;
import main.commands.search.filters.ComplexFilter;
import main.commands.search.filters.Filter;
import main.commands.search.filters.SimpleFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Search extends Command {

    private static final int MAX_RESULTS = 5;
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

    @Override
    public Result execute() {
        Program program = Program.getInstance();

        Stream<? extends Searchable> searchPlace;
        switch (type) {
            case "song":
                searchPlace = program.getLibrary().getSongs().stream();
                break;
            case "podcast":
                searchPlace = program.getPodcasts().stream();
                break;
            case "playlist":
                User user = program.getUsers().get(getUser());
                searchPlace = user.getPlaylists().stream();
                break;
            default:
                return null;
        }

        List<Searchable> valid = searchPlace.filter(this::itemMatchesFilters).limit(MAX_RESULTS)
            .collect(Collectors.toList());
        program.setSearchResults(valid);

        List<String> result = valid.stream().map(Searchable::getName).toList();

        return new Result(this, "Search returned " + result.size() + " results", result);
    }
}
