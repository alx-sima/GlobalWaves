package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.program.User;
import main.program.commands.Command;

public final class SwitchConnectionStatus extends Command {

    public SwitchConnectionStatus(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User caller = getCaller();
        if (caller == null) {
            return new MessageResult(this, "The username " + user + " doesn't exist.");
        }

        boolean newOnlineStatus = !caller.isOnline();
        caller.setOnline(newOnlineStatus, timestamp);

        return new MessageResult(this, user + " has changed status successfully.");
    }
}
