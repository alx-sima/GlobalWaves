package main.program.commands.playlist;

import fileio.input.commands.PlaylistCreateInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;

public final class CreatePlaylist extends NoOutputCommand {

    private final String playListName;

    public CreatePlaylist(final PlaylistCreateInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        if (caller.createPlaylist(playListName, timestamp)) {
            return "Playlist created successfully.";
        }
        return "A playlist with the same name already exists.";
    }
}
