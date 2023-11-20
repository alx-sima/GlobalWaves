package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.audio.queues.SongVisitor;
import main.program.User;
import main.program.commands.Command;

public final class Like extends Command {

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User callee = getCallee();
        Queue queue = callee.getPlayer().getQueue();
        callee.getPlayer().updateTime(timestamp);

        if (queue == null) {
            return new MessageResult(this, "Please load a source before liking or unliking.");
        }

        SongVisitor visitor = new SongVisitor();
        queue.accept(visitor);

        Song song = visitor.getCurrentSong();
        if (song == null) {
            return new MessageResult(this, "Loaded source is not a song.");
        }

        if (callee.like(song)) {
            return new MessageResult(this, "Like registered successfully.");
        }
        return new MessageResult(this, "Unlike registered successfully.");
    }
}
