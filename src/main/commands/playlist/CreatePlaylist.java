package main.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.MessageResult;
import main.Program;
import main.User;
import main.commands.Command;
import fileio.output.CommandResult;

public final class CreatePlaylist extends Command {

    private final String playListName;

    public CreatePlaylist(final CommandInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();

        User owner = instance.getUsers().get(getUser());
        if (owner.createPlaylist(playListName)) {
            return new MessageResult(this, "Playlist created successfully.");
        }
        return new MessageResult(this, "Eroare");
    }
}