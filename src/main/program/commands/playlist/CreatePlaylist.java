package main.program.commands.playlist;

import fileio.input.commands.PlaylistCreateInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class CreatePlaylist extends OnlineCommand {

    private final String playListName;

    public CreatePlaylist(final PlaylistCreateInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        User caller = getCaller();

        if (caller.createPlaylist(playListName, timestamp)) {
            resultBuilder.withMessage("Playlist created successfully.");
        } else {
            resultBuilder.withMessage("A playlist with the same name already exists.");
        }
        return resultBuilder.build();
    }
}
