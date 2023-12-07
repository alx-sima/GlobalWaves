package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.files.AudioFile;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Next extends OnlineCommand {

    public Next(final CommandInput input) {
        super(input);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();
        caller.getPlayer().updateTime(timestamp);

        if (queue != null) {
            AudioFile nextFile = queue.next();

            if (nextFile != null) {
                caller.getPlayer().setPaused(false, timestamp);
                return new MessageResult(this,
                    "Skipped to next track successfully. The current track is "
                        + nextFile.getName() + ".");
            }

            caller.getPlayer().clearQueue();
        }

        return new MessageResult(this, "Please load a source before skipping to the next track.");
    }
}
