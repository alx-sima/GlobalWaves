package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.audio.files.AudioFile;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Prev extends OnlineCommand {

    public Prev(final CommandInput input) {
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
            resultBuilder.withMessage(
                "Please load a source before returning to the previous track.");
            return resultBuilder.build();
        }

        AudioFile prevFile = queue.prev();
        caller.getPlayer().setPaused(false, timestamp);
        resultBuilder.withMessage(
            "Returned to previous track successfully. The current track is " + prevFile.getName()
                + ".");
        return resultBuilder.build();
    }
}
