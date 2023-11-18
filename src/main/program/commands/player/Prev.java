package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.queues.Queue;
import main.program.Program;
import main.program.commands.Command;
import main.audio.files.AudioFile;

public final class Prev extends Command {

    public Prev(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Queue queue = instance.getPlayer().getQueue();
        instance.getPlayer().updateTime(getTimestamp());

        if (queue == null) {
            return new MessageResult(this,
                "Please load a source before returning to the previous track.");
        }

        AudioFile prevFile = queue.prev();
        return new MessageResult(this,
            "Returned to previous track successfully. The current track is " + prevFile.getName()
                + ".");
    }
}
