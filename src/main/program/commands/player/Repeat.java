package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.audio.queues.Queue;
import main.audio.queues.RepeatMode;
import main.program.Player;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Repeat extends OnlineCommand {

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        User caller = getCaller();
        Player player = caller.getPlayer();
        Queue queue = player.getQueue();

        if (queue == null) {
            resultBuilder.withMessage("Please load a source before setting the repeat status.");
            return resultBuilder.build();
        }

        player.updateTime(timestamp);
        RepeatMode newMode = queue.changeRepeatMode();
        resultBuilder.withMessage(
            "Repeat mode changed to " + newMode.toString().toLowerCase() + ".");
        return resultBuilder.build();
    }
}
