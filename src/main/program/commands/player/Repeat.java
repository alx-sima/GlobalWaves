package main.program.commands.player;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.RepeatMode;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class Repeat extends NoOutputCommand {

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            return
                "Please load a source before setting the repeat status.";
        }

        RepeatMode newMode = queue.changeRepeatMode();
        return
            "Repeat mode changed to " + newMode.toString().toLowerCase() + ".";
    }
}
