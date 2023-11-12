package main.commands;

import fileio.input.FiltersInput;
import main.Program;
import main.User;
import main.audio.Searchable;
import main.audio.collections.Playlist;
import main.audio.collections.Podcast;
import main.audio.files.Song;
import main.commands.searchFilters.ComplexFilter;
import main.commands.searchFilters.Filter;
import main.commands.searchFilters.SimpleFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class Search extends Command {
    private final String type;
    private final List<Filter> filters = new ArrayList<>();

    public Search(final String command, final String user, final int timestamp, final String type
            , final FiltersInput filters) {
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

    private boolean itemMatchesFilters(Searchable item) {
        for (Filter filter : filters) {
            if (!filter.matchItem(item)) {
                return false;
            }
        }

        return true;
    }

    public Result execute() {
        Program program = Program.getInstance();
        List<String> result = new ArrayList<>();
        switch (type) {
            case "song":
                Stream<Song> songs = program.getLibrary().getSongs().stream();
                Stream<Song> validSongs = songs.filter(this::itemMatchesFilters);
                result = validSongs.map(Searchable::getName).toList();
                break;
            case "podcast":
                Stream<Podcast> podcasts = program.getPodcasts().stream();
                Stream<Podcast> validPodcasts = podcasts.filter(this::itemMatchesFilters);
                result = validPodcasts.map(Searchable::getName).toList();
                break;
            case "playlist":
                User user = null;
                for (User u : program.getUsers()) {
                    if (u.getUsername().equals(getUser())) {
                        user = u;
                        break;
                    }
                }
                Stream<Playlist> playlists = user.getPlaylists().stream();
                Stream<Playlist> validPlaylists = playlists.filter(this::itemMatchesFilters);
                result = validPlaylists.map(Searchable::getName).toList();
        }


        return new Result(getCommand(), getUser(), getTimestamp(),
                "Search returned " + result.size() + " results", result);
    }
}
