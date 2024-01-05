package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.audio.queues.Queue;
import main.entities.audio.queues.RepeatMode;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Repeat extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            return resultBuilder.returnMessage(
                "Please load a source before setting the repeat status.");
        }

        RepeatMode newMode = queue.changeRepeatMode();
        return resultBuilder.returnMessage(
            "Repeat mode changed to " + newMode.toString().toLowerCase() + ".");
    }
}
