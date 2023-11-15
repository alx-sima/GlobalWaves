package main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.UserInput;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import main.audio.Player;
import main.audio.Searchable;
import main.audio.collections.Library;
import main.audio.collections.Podcast;
import main.commands.Command;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Program {
    private static Program instance = null;
    private Map<String, User> users = new HashMap<>();
    private List<Podcast> podcasts = new ArrayList<>();
    private Library library;
    private List<Searchable> searchResults;
    private Searchable selectedResult = null;
    private Player player = null;

    private Program() {
    }

    /**
     * Get the instance of the program
     * (creates it if it isn't running).
     *
     * @return the instance
     */
    public static Program getInstance() {
        if (instance == null) {
            instance = new Program();
        }

        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(final Map<String,User> users) {
        this.users = users;
    }

    public List<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(final List<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(final Library library) {
        this.library = library;
    }

    public List<Searchable> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(final List<Searchable> searchResults) {
        this.searchResults = searchResults;
    }

    public Searchable getSelectedResult() {
        return selectedResult;
    }

    public void setSelectedResult(final Searchable selectedResult) {
        this.selectedResult = selectedResult;
    }

    /**
     * Run the program.
     *
     * @param libraryInput Parsed JSON of the libraries.
     * @param inputFile    File containing the commands for the program.
     * @param objectMapper The object mapper.
     * @param outputs      ArrayNode that will contain the outputs of the commands.
     * @throws IOException If the parser encounters an error.
     */
    public void run(final LibraryInput libraryInput, final String inputFile,
                    final ObjectMapper objectMapper, final ArrayNode outputs) throws IOException {
        library = new Library(libraryInput.getSongs());
        for (PodcastInput podcastInput : libraryInput.getPodcasts()) {
            podcasts.add(new Podcast(podcastInput));
        }
        for (UserInput userInput : libraryInput.getUsers()) {
            User user = new User(userInput);
            users.put(user.getUsername(), user);
        }

        List<CommandInput> commands = objectMapper.readValue(new File(inputFile),
                new TypeReference<>() {
        });
        for (CommandInput cmd : commands) {
            Command command = cmd.createCommand();
            if (command == null) {
                // TODO: unimplemented command!
                continue;
            }

            outputs.addPOJO(command.execute());
        }

    }
}
