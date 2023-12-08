package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.audio.files.AudioFile;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Next extends OnlineCommand {

    public Next(final CommandInput input) {
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

        if (queue != null) {
            AudioFile nextFile = queue.next();

            if (nextFile != null) {
                caller.getPlayer().setPaused(false, timestamp);
                resultBuilder.withMessage(
                    "Skipped to next track successfully. The current track is "
                        + nextFile.getName() + ".");
                return resultBuilder.build();
            }

            caller.getPlayer().clearQueue();
        }

        resultBuilder.withMessage("Please load a source before skipping to the next track.");
        return resultBuilder.build();
    }
}
