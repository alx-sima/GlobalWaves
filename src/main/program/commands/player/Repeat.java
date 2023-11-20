package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.collections.RepeatMode;
import main.audio.queues.Queue;
import main.program.Player;
import main.program.User;
import main.program.commands.Command;

public final class Repeat extends Command {

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User callee = getCallee();
        Player player = callee.getPlayer();
        Queue queue = player.getQueue();

        if (queue == null) {
            return new MessageResult(this,
                "Please load a source before setting the repeat status.");
        }

        player.updateTime(timestamp);
        RepeatMode newMode = queue.changeRepeatMode();
        return new MessageResult(this,
            "Repeat mode changed to " + newMode.toString().toLowerCase() + ".");
    }
}
