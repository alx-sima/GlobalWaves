package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.ShowPlaylistsResult;
import main.program.User;
import main.program.commands.Command;

public final class ShowPlaylists extends Command {

    public ShowPlaylists(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User caller = getCaller();
        return new ShowPlaylistsResult(this, caller.getPlaylists());
    }
}

