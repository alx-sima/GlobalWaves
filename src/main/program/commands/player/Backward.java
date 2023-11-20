package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.queues.Queue;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class Backward extends Command {

    private static final int BACKWARD_TIME = -90;

    public Backward(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Queue queue = user.getPlayer().getQueue();

        if (queue == null) {
            return new MessageResult(this, "Please load a source before rewinding.");
        }

        if (queue.skip(BACKWARD_TIME)) {
            return new MessageResult(this, "Rewound successfully.");
        } else {
            return new MessageResult(this, "The loaded source is not a podcast.");
        }
    }
}
