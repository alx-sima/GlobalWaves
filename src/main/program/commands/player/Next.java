package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.files.AudioFile;
import main.audio.queues.Queue;
import main.program.Program;
import main.program.commands.Command;

public final class Next extends Command {

    public Next(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Queue queue = instance.getPlayer().getQueue();
        instance.getPlayer().updateTime(getTimestamp());
        if (queue != null) {
            AudioFile nextFile = queue.next();

            if (nextFile != null) {
                return new MessageResult(this,
                    "Skipped to next track successfully. The current track is "
                        + nextFile.getName() + ".");
            }

            instance.getPlayer().clearQueue();
        }

        return new MessageResult(this, "Please load a source before skipping to the next track.");
    }
}
