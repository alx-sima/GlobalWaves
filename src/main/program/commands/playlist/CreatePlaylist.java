package main.program.commands.playlist;

import fileio.input.commands.PlaylistCreateInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

public final class CreatePlaylist extends OnlineUserCommand {

    @Getter
    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);
    private final String playListName;

    public CreatePlaylist(final PlaylistCreateInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        if (caller.createPlaylist(playListName, timestamp)) {
            resultBuilder.withMessage("Playlist created successfully.");
        } else {
            resultBuilder.withMessage("A playlist with the same name already exists.");
        }
        return resultBuilder;
    }
}
