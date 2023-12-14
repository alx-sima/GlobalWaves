package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Backward extends OnlineUserCommand {

    private static final int BACKWARD_TIME = -90;
    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public Backward(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
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
