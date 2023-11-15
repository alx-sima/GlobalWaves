package main.commands.player;

import fileio.input.CommandInput;
import fileio.output.MessageResult;
import main.Program;
import main.audio.Player;
import main.audio.collections.RepeatMode;
import main.commands.Command;
import fileio.output.CommandResult;

public final class Repeat extends Command {

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();
        if (player == null) {
            return new MessageResult(this, "Please load a source before setting the repeat status");
        }

        RepeatMode newMode = player.getQueue().changeRepeatMode();
        return new MessageResult(this,
            "Repeat mode changed to " + newMode.toString().toLowerCase() + " .");
    }
}
