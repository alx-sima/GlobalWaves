package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.StatusResult;
import main.program.Program;
import main.program.Player;
import fileio.output.StatusOutput;
import main.program.User;
import main.program.commands.Command;
import fileio.output.CommandResult;

public final class Status extends Command {

    public Status(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Player player = user.getPlayer();
        return new StatusResult(this, new StatusOutput(player, getTimestamp()));
    }
}
