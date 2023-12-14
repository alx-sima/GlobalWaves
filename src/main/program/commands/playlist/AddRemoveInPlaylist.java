package main.program.commands.playlist;

import fileio.input.commands.PlaylistOperationInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.collections.Playlist;
import main.entities.audio.files.Song;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

public final class AddRemoveInPlaylist extends OnlineUserCommand {

    @Getter
    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);
    private final int playlistId;

    public AddRemoveInPlaylist(final PlaylistOperationInput input) {
        super(input);
        playlistId = input.getPlaylistId();
    }

    @Override
    protected ResultBuilder execute(final User caller) {
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
