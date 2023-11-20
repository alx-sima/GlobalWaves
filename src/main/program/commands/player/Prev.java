package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.files.AudioFile;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.Command;

public final class Prev extends Command {

    public Prev(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User callee = getCallee();
        Queue queue = callee.getPlayer().getQueue();
        callee.getPlayer().updateTime(timestamp);

        if (queue == null) {
            return new MessageResult(this,
                "Please load a source before returning to the previous track.");
        }

        AudioFile prevFile = queue.prev();
        callee.getPlayer().setPaused(false, timestamp);
        return new MessageResult(this,
            "Returned to previous track successfully. The current track is " + prevFile.getName()
                + ".");
    }
}
