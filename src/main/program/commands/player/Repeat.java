package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.queues.Queue;
import main.entities.audio.queues.RepeatMode;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Repeat extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            return resultBuilder.withMessage(
                "Please load a source before setting the repeat status.");
        }

        RepeatMode newMode = queue.changeRepeatMode();
        return resultBuilder.withMessage(
            "Repeat mode changed to " + newMode.toString().toLowerCase() + ".");
    }
}
