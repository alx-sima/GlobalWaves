package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.audio.files.AudioFile;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Next extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    public Next(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        User caller = getCaller();
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
