package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;

@Getter
public final class Backward extends Command {

    private static final int BACKWARD_TIME = -90;
    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Backward(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Queue queue = caller.getPlayer().getQueue();

        if (queue == null) {
            return resultBuilder.returnMessage("Please load a source before rewinding.");
        }

        if (queue.skip(BACKWARD_TIME)) {
            return resultBuilder.returnMessage("Rewound successfully.");
        }
        return resultBuilder.returnMessage("The loaded source is not a podcast.");
    }
}
