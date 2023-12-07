package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Backward extends OnlineCommand {

    private static final int BACKWARD_TIME = -90;

    public Backward(final CommandInput input) {
        super(input);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();

        if (queue == null) {
            return new MessageResult(this, "Please load a source before rewinding.");
        }

        if (queue.skip(BACKWARD_TIME)) {
            return new MessageResult(this, "Rewound successfully.");
        } else {
            return new MessageResult(this, "The loaded source is not a podcast.");
        }
    }
}
