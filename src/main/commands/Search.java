package main.commands;

import main.Program;
import main.audio.Searchable;
import main.audio.files.Song;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Search extends Command {
    private final String type;
    private final Map<String, String> filters;

    public Search(final String command, final String user, final int timestamp, final String type
            , final Map<String, String> filters) {
        super(command, user, timestamp);
        this.type = type;
        this.filters = filters;
    }

    private boolean itemMatchesFilters(Searchable item) {
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            if (!item.matchFilter(filter.getKey(), filter.getValue())) {
                return false;
            }
        }

        return true;
    }

    public CommandResult execute() {
        Program program = Program.getInstance();
        Stream<Song> songs = program.getLibrary().getSongs().stream();
        Stream<Song> validSongs = songs.filter(this::itemMatchesFilters);
        List<String> songNames = validSongs.map(Song::getName).toList();

        return new CommandResult(getCommand(), getUser(), getTimestamp(),
                "Search returned " + songNames.size() + " results", songNames);
    }
}
