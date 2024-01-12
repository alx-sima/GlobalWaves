package main.program.commands.search;

import static main.program.Program.MAX_RESULTS;

import com.fasterxml.jackson.annotation.JsonFormat;
import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.SearchResult;
import fileio.output.SearchResult.Builder;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.databases.Library;
import main.program.databases.UserDatabase;
import main.program.entities.Searchable;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class Search extends Command {

    @Getter
    private final SearchResult.Builder resultBuilder = new Builder(this);
    private final String type;
    private final List<SearchFilter> filters;

    public Search(final Input input) {
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
            case "song" -> library.getSongs().stream();
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
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();

        Stream<? extends Searchable> searchPlace = getSearchPlace(caller, type);
        List<Searchable> valid = searchPlace.filter(this::itemMatchesFilters).limit(MAX_RESULTS)
            .collect(Collectors.toList());
        caller.getSearchbar().setSearchResults(valid);

        List<String> result = valid.stream().map(Searchable::getName).toList();

        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        player.clearQueue();

        return resultBuilder.results(result)
            .returnMessage("Search returned " + result.size() + " results");
    }

    @Getter
    @Setter
    public static final class Input extends CommandInput {

        @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
        private Map<String, List<String>> filters;
        private String type;

        @Override
        public Command createCommand() {
            return new Search(this);
        }

        /**
         * Create a list of filters for the search, based on the `filters` field.
         */
        public List<SearchFilter> createFilters() {
            return filters.entrySet().stream().map(entry -> {
                String filter = entry.getKey();
                List<String> parameters = entry.getValue();

                return new SearchFilter(filter, parameters);
            }).toList();
        }
    }
}
