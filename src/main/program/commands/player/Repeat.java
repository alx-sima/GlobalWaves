package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.RepeatMode;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

@Getter
public final class Repeat extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
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
