package main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.UserInput;
import main.audio.Searchable;
import main.audio.collections.Library;
import main.audio.collections.Podcast;
import main.commands.Command;
import main.commands.Search;
import main.commands.Select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Program {
    private static Program instance = null;

    private List<User> users;
    private List<Podcast> podcasts;
    private Library library;

    private List<Searchable> searchResults;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
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

    public void setSearchResults(List<Searchable> searchResults) {
        this.searchResults = searchResults;
    }

    /**
     * Run the program.
     *
     * @param library      Parsed JSON of the libraries.
     * @param inputFile    File containing the commands for the program.
     * @param objectMapper The object mapper.
     * @param outputs      ArrayNode that will contain the outputs of the commands.
     * @throws IOException If the parser encounters an error.
     */
    public void run(final LibraryInput library, final String inputFile,
                    final ObjectMapper objectMapper, ArrayNode outputs) throws IOException {
        Library songLibrary = new Library(library.getSongs());
        List<Podcast> podcasts = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (PodcastInput podcastInput : library.getPodcasts()) {
            podcasts.add(new Podcast(podcastInput));
        }
        for (UserInput userInput : library.getUsers()) {
            users.add(new User(userInput));
        }

        setLibrary(songLibrary);
        setPodcasts(podcasts);
        setUsers(users);

        List<CommandInput> commands = objectMapper.readValue(new File(inputFile),
                new TypeReference<>() {
        });
        for (CommandInput cmd : commands) {
            Command command;
            switch (cmd.getCommand()) {
                case "search":
                    command = new Search(cmd.getCommand(), cmd.getUsername(), cmd.getTimestamp(),
                            cmd.getType(), cmd.getFilters());
                    break;
                case "select":
                    command = new Select(cmd.getCommand(), cmd.getUsername(), cmd.getTimestamp(),
                            cmd.getItemNumber());
                    break;
                default:
                    return;
            }
            outputs.addPOJO(command.execute());
        }

    }
}
