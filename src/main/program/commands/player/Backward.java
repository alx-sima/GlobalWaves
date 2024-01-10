package main.program.commands.player;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;

public final class Backward extends NoOutputCommand {

    private static final int BACKWARD_TIME = -90;

    public Backward(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Queue queue = caller.getPlayer().getQueue();

        if (queue == null) {
            return "Please load a source before rewinding.";
        }

        if (queue.skip(BACKWARD_TIME)) {
            return "Rewound successfully.";
        }
        return "The loaded source is not a podcast.";
    }
}
