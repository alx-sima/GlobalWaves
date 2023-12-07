package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
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
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();

        if (queue == null) {
            return new MessageResult(this,
                "Please load a source before adding to or removing from the playlist.");
        }

        Song currentSong = queue.getCurrentSong();
        if (currentSong == null) {
            return new MessageResult(this, "The loaded source is not a song.");
        }

        Playlist playlist = caller.getPlaylist(playlistId - 1);
        if (playlist == null) {
            return new MessageResult(this, "The specified playlist does not exist.");
        }

        if (playlist.addRemoveSong(currentSong)) {
            return new MessageResult(this, "Successfully added to playlist.");
        }
        return new MessageResult(this, "Successfully removed from playlist.");
    }
}
