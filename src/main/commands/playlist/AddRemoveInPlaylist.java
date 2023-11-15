package main.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.Program;
import main.User;
import main.audio.Player;
import main.audio.CurrentSongVisitor;
import main.audio.collections.Queue;
import main.audio.files.Song;
import main.commands.Command;

public class AddRemoveInPlaylist extends Command {

    private final int playlistId;

    public AddRemoveInPlaylist(final CommandInput input) {
        super(input);
        playlistId = input.getPlaylistId();
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Player player = instance.getPlayer();
        if (player == null) {
            return new MessageResult(this,
                "Please load a source before adding to or removing from the playlist.");
        }
        Queue queue = player.getQueue();

        CurrentSongVisitor visitor = new CurrentSongVisitor();
        queue.getAudio().accept(visitor);

        Song currentSong = visitor.getPlayingSong();
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
