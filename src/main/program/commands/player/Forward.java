package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.queues.Queue;
import main.program.User;
import main.program.commands.Command;

public final class Forward extends Command {

    private static final int FORWARD_TIME = 90;

    public Forward(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User user = getCallee();
        Queue queue = user.getPlayer().getQueue();

        if (queue == null) {
            return new MessageResult(this, "Please load a source before attempting to forward.");
        }

        if (queue.skip(FORWARD_TIME)) {
            return new MessageResult(this, "Skipped forward successfully.");
        } else {
            return new MessageResult(this, "The loaded source is not a podcast.");
        }
    }
}
