package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatsResult;
import fileio.output.StatsResult.Builder;
import java.util.List;
import lombok.Getter;
import main.program.entities.audio.files.AudioFile;
import main.program.entities.users.User;
import main.program.commands.Command;

@Getter
public final class ShowPreferredSongs extends Command {

    private final StatsResult.Builder resultBuilder = new Builder(this);

    public ShowPreferredSongs(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User caller = getCaller();

        List<String> likes = caller.getLikedSongs().stream().map(AudioFile::getName).toList();
        return resultBuilder.result(likes).build();
    }
}
