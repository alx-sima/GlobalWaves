package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.files.AudioFile;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Prev extends OnlineCommand {

    public Prev(final CommandInput input) {
        super(input);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();
        caller.getPlayer().updateTime(timestamp);

        if (queue == null) {
            return new MessageResult(this,
                "Please load a source before returning to the previous track.");
        }

        AudioFile prevFile = queue.prev();
        caller.getPlayer().setPaused(false, timestamp);
        return new MessageResult(this,
            "Returned to previous track successfully. The current track is " + prevFile.getName()
                + ".");
    }
}
