package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.program.User;
import main.program.commands.Command;

public final class SwitchConnectionStatus extends Command {

    public SwitchConnectionStatus(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        MessageResultBuilder resultBuilder = new MessageResultBuilder(this);

        User caller = getCaller();
        if (caller == null) {
            resultBuilder.withMessage("The username " + user + " doesn't exist.");
            return resultBuilder.build();
        }

        boolean newOnlineStatus = !caller.isOnline();
        caller.setOnline(newOnlineStatus, timestamp);

        resultBuilder.withMessage(user + " has changed status successfully.");
        return resultBuilder.build();
    }
}
