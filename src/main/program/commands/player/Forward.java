package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.queues.Queue;
import main.program.commands.Command;
import main.program.Program;

public class Forward extends Command {

    public Forward(CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Queue queue = instance.getPlayer().getQueue();

        if (queue == null) {
            return new MessageResult(this, "Please load a source before attempting to forward.");
        }

        if (queue.skip(90)) {
            return new MessageResult(this, "Skipped forward successfully.");
        } else {
            return new MessageResult(this, "The loaded source is not a podcast.");
        }
    }
}
