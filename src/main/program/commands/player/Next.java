package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.files.AudioFile;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;

@Getter
public final class Next extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Next(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();

        Queue queue = caller.getPlayer().getQueue();
        caller.getPlayer().updateTime(timestamp);

        if (queue != null) {
            AudioFile nextFile = queue.next();

            if (nextFile != null) {
                caller.getPlayer().setPaused(false, timestamp);
                return resultBuilder.returnMessage(
                    "Skipped to next track successfully. The current track is "
                        + nextFile.getName() + ".");
            }

            caller.getPlayer().clearQueue();
        }

        return resultBuilder.returnMessage(
            "Please load a source before skipping to the next track.");
    }
}
