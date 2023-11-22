package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import java.util.List;
import main.audio.files.AudioFile;
import main.program.User;
import main.program.commands.Command;

public final class ShowPreferredSongs extends Command {

    public ShowPreferredSongs(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User caller = getCaller();

        List<String> likes = caller.getLikedSongs().stream().map(AudioFile::getName).toList();
        return new StatsResult(this, likes);
    }
}
