package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Like extends OnlineCommand {

    public Like(final CommandInput input) {
        super(input);
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
        caller.getPlayer().updateTime(timestamp);

        if (queue == null) {
            resultBuilder.withMessage("Please load a source before liking or unliking.");
            return resultBuilder.build();
        }

        Song song = queue.getCurrentSong();
        if (song == null) {
            resultBuilder.withMessage("Loaded source is not a song");
            return resultBuilder.build();
        }

        if (caller.like(song)) {
            resultBuilder.withMessage("Like registered successfully.");
        } else {
            resultBuilder.withMessage("Unlike registered successfully.");
        }
        return resultBuilder.build();
    }
}
