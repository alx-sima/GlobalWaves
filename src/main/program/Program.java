package main.program;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.UserInput;
import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import main.audio.collections.Library;
import main.program.commands.Command;

/**
 * The actual program, storing all the data and parsing commands.
 */
@Getter
public final class Program {

    private static Program instance = null;
    private final Map<String, User> users = new HashMap<>();
    private final Searchbar searchbar = new Searchbar();
    private Library library;

    private Program() {
    }

    /**
     * Get the instance of the program (creates it if it isn't running).
     */
    public static Program getInstance() {
        if (instance == null) {
            instance = new Program();
        }

        return instance;
    }

    private void initializeData(final LibraryInput libraryInput) {
        library = new Library(libraryInput);

        for (UserInput userInput : libraryInput.getUsers()) {
            User user = new User(userInput);
            users.put(user.getUsername(), user);
        }
    }

    private void executeCommands(final List<CommandInput> commands, final ArrayNode outputs) {
        for (CommandInput cmd : commands) {
            Command command = cmd.createCommand();
            if (command == null) {
                System.err.println("Invalid command: " + cmd.getCommand());
                System.exit(-1);
            }

            CommandResult output = command.execute();
            outputs.addPOJO(output);
        }
    }

    private void clearData() {
        library = null;
        users.clear();
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

        initializeData(libraryInput);

        List<CommandInput> commands = objectMapper.readValue(new File(inputFile),
            new TypeReference<>() {
            });
        executeCommands(commands, outputs);

        clearData();
    }
}
