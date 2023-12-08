package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Backward extends OnlineCommand {

    private static final int BACKWARD_TIME = -90;

    public Backward(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();

        if (queue == null) {
            resultBuilder.withMessage("Please load a source before rewinding.");
            return resultBuilder.build();
        }

        if (queue.skip(BACKWARD_TIME)) {
            resultBuilder.withMessage("Rewound successfully.");
        } else {
            resultBuilder.withMessage("The loaded source is not a podcast.");
        }
        return resultBuilder.build();
    }
}
