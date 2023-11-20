package main.program.commands.search;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.ShowPreferredSongsResult;
import java.util.Comparator;
import java.util.List;
import main.audio.collections.Library;
import main.audio.files.Song;
import main.program.Program;
import main.program.commands.Command;

public final class GetTop5Songs extends Command {

    public GetTop5Songs(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Library library = instance.getLibrary();

        List<String> top = library.getSongs().stream().sorted(Comparator.comparingInt(Song::getLikes).reversed())
            .limit(5).map(Song::getName).toList();

        return new ShowPreferredSongsResult(this, top);
    }
}
