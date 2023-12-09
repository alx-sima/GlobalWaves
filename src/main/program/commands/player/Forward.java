package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Forward extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private static final int FORWARD_TIME = 90;

    public Forward(final CommandInput input) {
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

        if (queue == null) {
            return resultBuilder.withMessage("Please load a source before attempting to forward.");
        }

        if (queue.skip(FORWARD_TIME)) {
            resultBuilder.withMessage("Skipped forward successfully.");
        } else {
            resultBuilder.withMessage("The loaded source is not a podcast.");
        }
        return resultBuilder;
    }
}
