package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import java.util.List;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.databases.Library;
import main.program.entities.audio.collections.Playlist;

public final class SwitchVisibility extends NoOutputCommand {

    private final int playlistId;

    public SwitchVisibility(final PlaylistOperationInput input) {
        super(input);
        playlistId = input.getPlaylistId();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        List<Playlist> playlists = new RequireUserOnline(user).check().getPlaylists();

        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
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
        return "Visibility status updated successfully to " + visibilityStatus + ".";
    }
}
