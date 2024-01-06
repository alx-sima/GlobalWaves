package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Backward extends OnlineUserCommand {

    private static final int BACKWARD_TIME = -90;
    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Backward(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
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
