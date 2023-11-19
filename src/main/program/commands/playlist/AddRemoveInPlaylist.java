package main.program.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import java.util.ArrayList;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.audio.queues.SongVisitor;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class AddRemoveInPlaylist extends Command {

    private final int playlistId;

    public AddRemoveInPlaylist(final CommandInput input) {
        super(input);
        playlistId = input.getPlaylistId();
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Queue queue = instance.getPlayer().getQueue();

        if (queue == null) {
            return new MessageResult(this,
                "Please load a source before adding to or removing from the playlist.");
        }

        SongVisitor visitor = new SongVisitor();
        queue.accept(visitor);

        Song currentSong = visitor.getCurrentSong();
        if (currentSong == null) {
            return new MessageResult(this, "The loaded source is not a song.");
        }

        try {
            if (user.addRemoveSongInPlaylist(currentSong, playlistId - 1)) {
                return new MessageResult(this, "Successfully added to playlist.");
            }
            return new MessageResult(this, "Successfully removed from playlist.");
        } catch (IndexOutOfBoundsException e) {
            return new MessageResult(this, "The specified playlist does not exist.");
        }
    }
}
