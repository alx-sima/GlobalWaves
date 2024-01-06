package main.program.commands.playlist;

import fileio.input.commands.PlaylistCreateInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

public final class CreatePlaylist extends OnlineUserCommand {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final String playListName;

    public CreatePlaylist(final PlaylistCreateInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    protected MessageResult execute(final User caller) {
        if (caller.createPlaylist(playListName, timestamp)) {
            return resultBuilder.returnMessage("Playlist created successfully.");
        }
        return resultBuilder.returnMessage("A playlist with the same name already exists.");
    }
}
