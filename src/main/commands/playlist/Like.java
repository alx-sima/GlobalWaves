package main.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.Program;
import main.User;
import main.audio.Player;
import main.audio.SongVisitor;
import main.audio.collections.Queue;
import main.audio.files.Song;
import main.commands.Command;

public class Like extends Command {

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Player player = instance.getPlayer();
        if (player == null) {
            return new MessageResult(this, "Please load a source before liking or unliking.");
        }
        Queue queue = player.getQueue();
        SongVisitor visitor = new SongVisitor();
        queue.getAudio().accept(visitor);

        Song song = visitor.getContainedSong();
        if (song == null) {

            return new MessageResult(this, "Loaded source is not a song.");
        }

        if (user.like(song)) {
            return new MessageResult(this, "Like registered successfully.");
        }
        return new MessageResult(this, "Unlike registered successfully.");
    }
}
