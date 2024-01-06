package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

public final class AddRemoveInPlaylist extends OnlineUserCommand {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final int playlistId;

    public AddRemoveInPlaylist(final PlaylistOperationInput input) {
        super(input);
        playlistId = input.getPlaylistId();
    }

    @Override
    protected MessageResult execute(final User caller) {
        Queue queue = caller.getPlayer().getQueue();

        if (queue == null) {
            return resultBuilder.returnMessage(
                "Please load a source before adding to or removing from the playlist.");
        }

        Song currentSong = queue.getCurrentSong();
        if (currentSong == null) {
            return resultBuilder.returnMessage("The loaded source is not a song.");
        }

        Playlist playlist = caller.getPlaylist(playlistId - 1);
        if (playlist == null) {
            return resultBuilder.returnMessage("The specified playlist does not exist.");
        }

        if (playlist.addRemoveSong(currentSong)) {
            return resultBuilder.returnMessage("Successfully added to playlist.");
        }
        return resultBuilder.returnMessage("Successfully removed from playlist.");
    }
}
