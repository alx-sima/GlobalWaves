package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import java.util.List;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class SwitchVisibility extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final int playlistId;

    public SwitchVisibility(final PlaylistOperationInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        playlistId = input.getPlaylistId();
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Program program = Program.getInstance();
        User caller = getCaller();
        List<Playlist> playlists = caller.getPlaylists();

        if (playlistId > playlists.size()) {
            return resultBuilder.withMessage("The specified playlist ID is too high.");
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
        return resultBuilder.withMessage(
            "Visibility status updated successfully to " + visibilityStatus + ".");
    }
}
