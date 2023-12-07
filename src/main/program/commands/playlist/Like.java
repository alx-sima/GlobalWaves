package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Like extends OnlineCommand {

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();
        caller.getPlayer().updateTime(timestamp);

        if (queue == null) {
            return new MessageResult(this, "Please load a source before liking or unliking.");
        }

        Song song = queue.getCurrentSong();
        if (song == null) {
            return new MessageResult(this, "Loaded source is not a song.");
        }

        if (caller.like(song)) {
            return new MessageResult(this, "Like registered successfully.");
        }
        return new MessageResult(this, "Unlike registered successfully.");
    }
}
