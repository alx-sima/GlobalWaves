package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Forward extends OnlineUserCommand {

    private static final int FORWARD_TIME = 90;
    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Forward(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            return resultBuilder.returnMessage(
                "Please load a source before attempting to forward.");
        }

        if (queue.skip(FORWARD_TIME)) {
            return resultBuilder.returnMessage("Skipped forward successfully.");
        }
        return resultBuilder.returnMessage("The loaded source is not a podcast.");
    }
}
