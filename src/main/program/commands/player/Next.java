package main.program.commands.player;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.files.AudioFile;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;

public final class Next extends NoOutputCommand {

    public Next(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();

        Queue queue = caller.getPlayer().getQueue();
        caller.getPlayer().updateTime(timestamp);

        if (queue != null) {
            AudioFile nextFile = queue.next();

            if (nextFile != null) {
                caller.getPlayer().setPaused(false, timestamp);
                return
                    "Skipped to next track successfully. The current track is "
                        + nextFile.getName() + ".";
            }

            caller.getPlayer().clearQueue();
        }

        return
            "Please load a source before skipping to the next track.";
    }
}
