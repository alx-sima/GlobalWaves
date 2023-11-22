package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import java.util.List;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class SwitchVisibility extends Command {

    private final int playlistId;

    public SwitchVisibility(final PlaylistOperationInput input) {
        super(input);
        this.playlistId = input.getPlaylistId();
    }

    @Override
    public CommandResult execute() {
        Program program = Program.getInstance();
        User caller = getCaller();
        List<Playlist> playlists = caller.getPlaylists();

        if (playlistId > playlists.size()) {
            return new MessageResult(this, "The specified playlist ID is too high.");
        }

        List<Playlist> publicPlaylists = program.getLibrary().getPublicPlaylists();
        Playlist playlist = playlists.get(playlistId - 1);

        if (playlist.isPrivate()) {
            playlist.setPrivate(false);
            publicPlaylists.add(playlist);
        } else {
            playlist.setPrivate(true);
            publicPlaylists.remove(playlist);
        }

        String visibilityStatus = playlist.isPrivate() ? "private" : "public";
        return new MessageResult(this,
            "Visibility status updated successfully to " + visibilityStatus + ".");
    }
}
