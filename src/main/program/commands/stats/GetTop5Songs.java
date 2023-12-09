package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.Comparator;
import java.util.List;
import main.entities.audio.collections.Library;
import main.entities.audio.files.Song;
import main.program.Program;
import main.program.commands.Command;

public final class GetTop5Songs extends Command {

    public GetTop5Songs(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program program = Program.getInstance();
        Library library = program.getLibrary();

        // Compare by number of likes.
        Comparator<Song> comparator = Comparator.comparingInt(Song::getLikes).reversed();

        List<String> top = library.getSongs().stream().sorted(comparator).limit(MAX_RESULTS)
            .map(Song::getName).toList();

        return new StatsResult(this, top);
    }
}
