package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
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
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();
        Player player = caller.getPlayer();
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
