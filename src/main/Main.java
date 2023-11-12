package main;

import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.CommandInput;
import fileio.input.LibraryInput;
import fileio.input.PodcastInput;
import fileio.input.UserInput;
import main.audio.collections.Library;
import main.audio.collections.Podcast;
import main.commands.Command;
import main.commands.Search;
import main.commands.Select;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput  for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePathInput, final String filePathOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);

        ArrayNode outputs = objectMapper.createArrayNode();

        Program programInstance = Program.getInstance();
        Library songLibrary = new Library(library.getSongs());
        List<Podcast> podcasts = new ArrayList<>();
        List<User> users = new ArrayList<>();

        for (PodcastInput podcastInput : library.getPodcasts()) {
            podcasts.add(new Podcast(podcastInput));
        }
        for (UserInput userInput : library.getUsers()) {
            users.add(new User(userInput));
        }

        programInstance.setLibrary(songLibrary);
        programInstance.setPodcasts(podcasts);
        programInstance.setUsers(users);

        List<CommandInput> commands =
                objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePathInput),
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

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }
}
