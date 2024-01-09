package main.program.commands.playlist;

import fileio.input.commands.PlaylistCreateInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;

public final class CreatePlaylist extends Command {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final String playListName;

    public CreatePlaylist(final PlaylistCreateInput input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        if (caller.createPlaylist(playListName, timestamp)) {
            return resultBuilder.returnMessage("Playlist created successfully.");
        }
        return resultBuilder.returnMessage("A playlist with the same name already exists.");
    }
}
