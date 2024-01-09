package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import java.util.List;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.users.User;
import main.program.databases.Library;

public final class SwitchVisibility extends Command {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final int playlistId;

    public SwitchVisibility(final PlaylistOperationInput input) {
        super(input);
        playlistId = input.getPlaylistId();
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        List<Playlist> playlists = new RequireUserOnline(user).check().getPlaylists();

        if (playlistId > playlists.size()) {
            return resultBuilder.returnMessage("The specified playlist ID is too high.");
        }

        List<Playlist> publicPlaylists = Library.getInstance().getPublicPlaylists();
        Playlist playlist = playlists.get(playlistId - 1);

        if (playlist.isPrivate()) {
            playlist.setPrivate(false);
            publicPlaylists.add(playlist);
        } else {
            playlist.setPrivate(true);
            publicPlaylists.remove(playlist);
        }

        String visibilityStatus = playlist.isPrivate() ? "private" : "public";
        return resultBuilder.returnMessage(
            "Visibility status updated successfully to " + visibilityStatus + ".");
    }
}
