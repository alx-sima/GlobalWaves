package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.StatusOutput;
import fileio.output.StatusResult;
import main.program.Player;
import main.program.User;
import main.program.commands.Command;

public final class Status extends Command {

    public Status(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User callee = getCallee();
        Player player = callee.getPlayer();
        return new StatusResult(this, new StatusOutput(player, timestamp));
    }
}
