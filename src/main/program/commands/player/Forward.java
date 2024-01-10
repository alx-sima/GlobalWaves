package main.program.commands.player;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class Forward extends NoOutputCommand {

    private static final int FORWARD_TIME = 90;

    public Forward(final CommandInput input) {
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
                "Please load a source before attempting to forward.";
        }

        if (queue.skip(FORWARD_TIME)) {
            return "Skipped forward successfully.";
        }
        return "The loaded source is not a podcast.";
    }
}
