package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.files.AudioFile;
import main.audio.queues.Queue;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class Next extends Command {

    public Next(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Queue queue = user.getPlayer().getQueue();
        user.getPlayer().updateTime(getTimestamp());
        if (queue != null) {
            AudioFile nextFile = queue.next();

            if (nextFile != null) {
                user.getPlayer().setPaused(false, getTimestamp());
                return new MessageResult(this,
                    "Skipped to next track successfully. The current track is "
                        + nextFile.getName() + ".");
            }

            user.getPlayer().clearQueue();
        }

        return new MessageResult(this, "Please load a source before skipping to the next track.");
    }
}
