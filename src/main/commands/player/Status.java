package main.commands.player;

import fileio.input.CommandInput;
import fileio.output.StatusResult;
import main.Program;
import main.audio.Player;
import main.audio.PlayerStatus;
import main.commands.Command;
import fileio.output.CommandResult;

public final class Status extends Command {

    public Status(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();
        return new StatusResult(this, new PlayerStatus(player, getTimestamp()));
    }
}
