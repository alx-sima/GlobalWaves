package main.program.commands.playlist;

import fileio.input.commands.PlaylistCreateInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class CreatePlaylist extends OnlineCommand {

    private final String playListName;

    public CreatePlaylist(final PlaylistCreateInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();

        if (caller.createPlaylist(playListName, timestamp)) {
            return new MessageResult(this, "Playlist created successfully.");
        }
        return new MessageResult(this, "A playlist with the same name already exists.");
    }
}
