package main.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.ShowPreferredSongsResult;
import java.util.List;
import main.Program;
import main.User;
import main.audio.Searchable;
import main.audio.files.AudioFile;
import main.commands.Command;

public class ShowPreferredSongs extends Command {

    public ShowPreferredSongs(CommandInput input) {
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
