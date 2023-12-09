package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Backward extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private static final int BACKWARD_TIME = -90;

    public Backward(final CommandInput input) {
        super(input );
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
            return resultBuilder.withMessage("Please load a source before rewinding.");
        }

        if (queue.skip(BACKWARD_TIME)) {
            resultBuilder.withMessage("Rewound successfully.");
        } else {
            resultBuilder.withMessage("The loaded source is not a podcast.");
        }
        return resultBuilder;
    }
}
