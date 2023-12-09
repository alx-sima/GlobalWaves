package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.audio.collections.Playlist;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class AddRemoveInPlaylist extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final int playlistId;

    public AddRemoveInPlaylist(final PlaylistOperationInput input) {
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
        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();

        if (queue == null) {
            return resultBuilder.withMessage(
                "Please load a source before adding to or removing from the playlist.");
        }

        Song currentSong = queue.getCurrentSong();
        if (currentSong == null) {
            return resultBuilder.withMessage("The loaded source is not a song");
        }

        Playlist playlist = caller.getPlaylist(playlistId - 1);
        if (playlist == null) {
            return resultBuilder.withMessage("The specified playlist does not exist.");
        }

        if (playlist.addRemoveSong(currentSong)) {
            resultBuilder.withMessage("Successfully added to playlist.");
        } else {
            resultBuilder.withMessage("Successfully removed from playlist.");
        }
        return resultBuilder;
    }
}
