package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Forward extends OnlineUserCommand {

    private static final int FORWARD_TIME = 90;
    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public Forward(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

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
