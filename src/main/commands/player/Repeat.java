package main.commands.player;

import fileio.input.CommandInput;
import main.Program;
import main.audio.Player;
import main.audio.collections.RepeatMode;
import main.commands.Command;
import main.commands.Result;

public final class Repeat extends Command {

    public Repeat(CommandInput input) {
        super(input);
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();
        if (player == null) {
            return new Result(this, "Please load a source before setting the repeat status");
        }

        RepeatMode newMode = player.getQueue().changeRepeatMode();
        return new Result(this,
            "Repeat mode changed to " + newMode.toString().toLowerCase() + " .");
    }
}
