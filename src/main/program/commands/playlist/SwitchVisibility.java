package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.builders.ResultBuilder;
import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Playlist;
import main.entities.users.User;
import main.program.Library;
import main.program.commands.user.OnlineUserCommand;

public final class SwitchVisibility extends OnlineUserCommand {

    @Getter
    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);
    private final int playlistId;

    public SwitchVisibility(final PlaylistOperationInput input) {
        super(input);
        playlistId = input.getPlaylistId();
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        List<Playlist> playlists = caller.getPlaylists();

        if (playlistId > playlists.size()) {
            return resultBuilder.withMessage("The specified playlist ID is too high.");
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
        return resultBuilder.withMessage(
            "Visibility status updated successfully to " + visibilityStatus + ".");
    }
}
