package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.users.User;
import main.entities.users.UserDatabase;

@Getter
public final class SwitchConnectionStatus extends UserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public SwitchConnectionStatus(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder executeFor(final User target) {
        if (!UserDatabase.getInstance().getUsers().contains(target)) {
            return resultBuilder.withMessage(user + " is not a normal user.");
        }
        boolean newOnlineStatus = !target.isOnline();
        target.setOnline(newOnlineStatus, timestamp);

        return resultBuilder.withMessage(user + " has changed status successfully.");
    }
}
