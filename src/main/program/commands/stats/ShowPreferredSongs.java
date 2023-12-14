package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.StatsResultBuilder;
import java.util.List;
import lombok.Getter;
import main.entities.audio.files.AudioFile;
import main.entities.users.User;
import main.program.commands.Command;

@Getter
public final class ShowPreferredSongs extends Command {

    private final StatsResultBuilder resultBuilder = new StatsResultBuilder().withCommand(this);

    public ShowPreferredSongs(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User caller = getCaller();

        List<String> likes = caller.getLikedSongs().stream().map(AudioFile::getName).toList();
        return resultBuilder.withResult(likes).build();
    }
}
