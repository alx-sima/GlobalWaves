package main.commands.playlist;

import fileio.input.CommandInput;
import main.Program;
import main.User;
import main.commands.Command;
import main.commands.Result;

public class CreatePlaylist extends Command {

    private final String playListName;

    public CreatePlaylist(final CommandInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();

        User owner = instance.getUsers().get(getUser());
        if (owner.createPlaylist(playListName)) {
            return new Result(this, "Playlist created successfully.");
        }
        return new Result(this, "Eroare");
    }
}
