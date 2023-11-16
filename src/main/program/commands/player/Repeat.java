package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.queues.Queue;
import main.program.Program;
import main.program.Player;
import main.audio.collections.RepeatMode;
import main.program.commands.Command;

public final class Repeat extends Command {

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();
        Queue queue = player.getQueue();

        if (queue == null) {
            return new MessageResult(this,
                "Please load a source before setting the repeat status.");
        }

        player.updateTime(getTimestamp());
        RepeatMode newMode = queue.changeRepeatMode();
        return new MessageResult(this,
            "Repeat mode changed to " + newMode.toString().toLowerCase() + ".");
    }
}
