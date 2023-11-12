package main.commands;

import fileio.input.FiltersInput;
import main.Program;
import main.User;
import main.audio.Searchable;
import main.commands.searchFilters.ComplexFilter;
import main.commands.searchFilters.Filter;
import main.commands.searchFilters.SimpleFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Search extends Command {
    private static final int MAX_RESULTS = 5;
    private final String type;
    private final List<Filter> filters = new ArrayList<>();

    public Search(final String command, final String user, final int timestamp, final String type, final FiltersInput filters) {
        super(command, user, timestamp);
        this.type = type;

        // TODO: refactor later
        if (filters.getName() != null) {
            this.filters.add(new SimpleFilter("name", filters.getName()));
        }
        if (filters.getAlbum() != null) {
            this.filters.add(new SimpleFilter("album", filters.getAlbum()));
        }
        if (filters.getTags() != null) {
            this.filters.add(new ComplexFilter("tags", filters.getTags()));
        }
        if (filters.getLyrics() != null) {
            this.filters.add(new SimpleFilter("lyrics", filters.getLyrics()));
        }
        if (filters.getGenre() != null) {
            this.filters.add(new SimpleFilter("genre", filters.getGenre()));
        }
        if (filters.getReleaseYear() != null) {
            this.filters.add(new SimpleFilter("releaseYear", filters.getReleaseYear()));
        }
        if (filters.getArtist() != null) {
            this.filters.add(new SimpleFilter("artist", filters.getArtist()));
        }
        if (filters.getOwner() != null) {
            this.filters.add(new SimpleFilter("owner", filters.getOwner()));
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
                User user = null;
                for (User u : program.getUsers()) {
                    if (u.getUsername().equals(getUser())) {
                        user = u;
                        break;
                    }
                }
                searchPlace = user.getPlaylists().stream();
                break;
            default:
                return null;
        }

        List<Searchable> valid = searchPlace.filter(this::itemMatchesFilters).limit(MAX_RESULTS).collect(Collectors.toList());
        program.setSearchResults(valid);

        List<String> result = valid.stream().map(Searchable::getName).toList();

        return new Result(this, "Search returned " + result.size() + " results", result);
    }
}
