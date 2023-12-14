package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.files.AudioFile;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Next extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public Next(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        Queue queue = caller.getPlayer().getQueue();
        caller.getPlayer().updateTime(timestamp);

        if (queue != null) {
            AudioFile nextFile = queue.next();

            if (nextFile != null) {
                caller.getPlayer().setPaused(false, timestamp);
                return resultBuilder.withMessage(
                    "Skipped to next track successfully. The current track is "
                        + nextFile.getName() + ".");
            }

            caller.getPlayer().clearQueue();
        }

        return resultBuilder.withMessage("Please load a source before skipping to the next track.");
    }
}
