package main.program;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.UserInput;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import main.audio.Searchable;
import main.audio.collections.Library;
import main.audio.collections.Playlist;
import main.audio.collections.Podcast;
import main.program.commands.Command;

@Getter
public final class Program {

    private static Program instance = null;
    private final Map<String, User> users = new HashMap<>();
    @Getter
    private final List<Playlist> publicPlaylists = new ArrayList<>();
    private final Player player = new Player();
    private List<Podcast> podcasts = new ArrayList<>();
    private Library library;
    @Setter
    private List<Searchable> searchResults;
    @Setter
    private Searchable selectedResult = null;

    private Program() {
    }

    /**
     * Get the instance of the program (creates it if it isn't running).
     *
     * @return the instance
     */
    public static Program getInstance() {
        if (instance == null) {
            instance = new Program();
        }

        return instance;
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
        List<Podcast> podcasts2 = new ArrayList<>();
        for (PodcastInput podcastInput : libraryInput.getPodcasts()) {
            podcasts2.add(new Podcast(podcastInput));
        }
        podcasts = podcasts2;

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

        publicPlaylists.clear();
    }
}
