package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.audio.files.AudioFile;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Next extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Next(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
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
