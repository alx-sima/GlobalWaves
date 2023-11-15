package main.commands.playlist;

import fileio.input.CommandInput;
import java.util.List;
import main.Program;
import main.User;
import main.audio.collections.Playlist;
import main.commands.Command;
import main.commands.Result;


public class ShowPlaylists extends Command {

    public ShowPlaylists(final CommandInput input) {
        super(input);
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());

        return new ShowPlaylistsResult(this, user.getPlaylists());
    }
}

class ShowPlaylistsResult extends Result {

    private final List<Playlist> result;

    public ShowPlaylistsResult(final Command command, final List<Playlist> playlists) {
        super(command, null, null);
        result = playlists;
    }

    public List<Playlist> getResult() {
        return result;
    }
}