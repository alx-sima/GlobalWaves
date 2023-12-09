package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.audio.files.AudioFile;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Prev extends DependentCommand {

    private final MessageResultBuilder resultBuilder;

    public Prev(final CommandInput input) {
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

        if (queue == null) {
            return resultBuilder.withMessage(
                "Please load a source before returning to the previous track.");
        }

        AudioFile prevFile = queue.prev();
        caller.getPlayer().setPaused(false, timestamp);
        return resultBuilder.withMessage(
            "Returned to previous track successfully. The current track is " + prevFile.getName()
                + ".");
    }
}
