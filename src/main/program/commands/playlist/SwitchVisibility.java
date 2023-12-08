package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import java.util.List;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class SwitchVisibility extends OnlineCommand {

    private final int playlistId;

    public SwitchVisibility(final PlaylistOperationInput input) {
        super(input);
        this.playlistId = input.getPlaylistId();
    }

    @Override
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        Program program = Program.getInstance();
        User caller = getCaller();
        List<Playlist> playlists = caller.getPlaylists();

        if (playlistId > playlists.size()) {
            resultBuilder.withMessage("The specified playlist ID is too high.");
            return resultBuilder.build();
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
        resultBuilder.withMessage(
            "Visibility status updated successfully to " + visibilityStatus + ".");
        return resultBuilder.build();
    }
}
