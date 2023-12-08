package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.audio.collections.Playlist;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class AddRemoveInPlaylist extends OnlineCommand {

    private final int playlistId;

    public AddRemoveInPlaylist(final PlaylistOperationInput input) {
        super(input);
        playlistId = input.getPlaylistId();
    }

    @Override
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();

        if (queue == null) {
            resultBuilder.withMessage(
                "Please load a source before adding to or removing from the playlist.");
            return resultBuilder.build();
        }

        Song currentSong = queue.getCurrentSong();
        if (currentSong == null) {
            resultBuilder.withMessage("The loaded source is not a song");
            return resultBuilder.build();
        }

        Playlist playlist = caller.getPlaylist(playlistId - 1);
        if (playlist == null) {
            resultBuilder.withMessage("The specified playlist does not exist.");
            return resultBuilder.build();
        }

        if (playlist.addRemoveSong(currentSong)) {
            resultBuilder.withMessage("Successfully added to playlist.");
        } else {
            resultBuilder.withMessage("Successfully removed from playlist.");
        }
        return resultBuilder.build();
    }
}
