package main.program.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.ShowPreferredSongsResult;
import java.util.List;
import main.program.Program;
import main.program.User;
import main.audio.files.AudioFile;
import main.program.commands.Command;

public final class ShowPreferredSongs extends Command {

    public ShowPreferredSongs(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());

        List<String> likes = user.getLikedSongs().stream().map(AudioFile::getName).toList();

        return new ShowPreferredSongsResult(this, likes);
    }
}
