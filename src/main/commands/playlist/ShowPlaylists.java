package main.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.ShowPlaylistsResult;
import main.Program;
import main.User;
import main.commands.Command;
import fileio.output.CommandResult;


public final class ShowPlaylists extends Command {

    public ShowPlaylists(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());

        return new ShowPlaylistsResult(this, user.getPlaylists());
    }
}

