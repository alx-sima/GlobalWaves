package main.commands.player;

import fileio.input.CommandInput;
import main.Program;
import main.audio.Player;
import main.audio.PlayerStatus;
import main.commands.Command;
import main.commands.Result;

public final class Status extends Command {
    public Status(final CommandInput input) {
        super(input);
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();
        return new Result(this, new PlayerStatus(player, getTimestamp()));
    }
}
