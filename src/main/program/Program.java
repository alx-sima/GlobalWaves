package main.program;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.LibraryInput;
import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.Getter;
import main.program.databases.Library;
import main.program.databases.UserDatabase;
import main.program.commands.Command;
import main.program.commands.stats.EndProgram;

/**
 * The actual program, storing all the data and parsing commands.
 */
@Getter
public final class Program {

    /**
     * The maximum number of results to be displayed.
     */
    public static final int MAX_RESULTS = 5;

    private final LibraryInput libraryInput;
    private final String inputFile;
    private final ObjectMapper objectMapper;
    private final ArrayNode outputs;

    public Program(final LibraryInput libraryInput, final String inputFile,
        final ObjectMapper objectMapper,
        final ArrayNode outputs) {
        this.libraryInput = libraryInput;
        this.inputFile = inputFile;
        this.objectMapper = objectMapper;
        this.outputs = outputs;
    }

    private void executeCommands(final List<CommandInput> commands, final ArrayNode outputNodes) {
        for (CommandInput cmd : commands) {
            Command command = cmd.createCommand();
            if (command == null) {
                System.err.println("Invalid command: " + cmd.getCommand());
                System.exit(-1);
            }

            CommandResult output = command.run();
            outputNodes.addPOJO(output);
        }

        CommandInput lastCommand = commands.get(commands.size() - 1);
        outputNodes.addPOJO(new EndProgram(lastCommand.getTimestamp()));
    }

    /**
     * Run the program.
     *
     * @throws IOException if a JSON parsing error occurs.
     */
    public void run() throws IOException {

        UserDatabase.getInstance().initializeDatabase(libraryInput);
        Library.getInstance().initializeLibrary(libraryInput);

        List<CommandInput> commands = objectMapper.readValue(new File(inputFile),
            new TypeReference<>() {
            });
        executeCommands(commands, outputs);
    }
}
