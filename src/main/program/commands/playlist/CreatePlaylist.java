package main.program.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.program.User;
import main.program.commands.Command;

public final class CreatePlaylist extends Command {

    private final String playListName;

    public CreatePlaylist(final CommandInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    public CommandResult execute() {
        User callee = getCallee();

        if (callee.createPlaylist(playListName, timestamp)) {
            return new MessageResult(this, "Playlist created successfully.");
        }
        return new MessageResult(this, "A playlist with the same name already exists.");
    }
}
