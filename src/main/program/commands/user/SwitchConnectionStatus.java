package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.databases.UserDatabase;

@Getter
public final class SwitchConnectionStatus extends UserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public SwitchConnectionStatus(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult executeFor(final User target) {
        if (!UserDatabase.getInstance().getUsers().contains(target)) {
            return resultBuilder.returnMessage(user + " is not a normal user.");
        }
        boolean newOnlineStatus = !target.isOnline();
        target.setOnline(newOnlineStatus, timestamp);

        return resultBuilder.returnMessage(user + " has changed status successfully.");
    }
}
