package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.builders.ResultBuilder;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.program.commands.Command;

/**
 * A command that can be executed only for existing users.
 */
public abstract class UserCommand extends Command {

    protected UserCommand(final CommandInput input) {
        super(input);
    }

    @Override
    public final CommandResult execute() {
        User target = UserDatabase.getInstance().getUser(user);
        if (target == null) {
            return getResultBuilder().withMessage("The username " + user + " doesn't exist.")
                .build();
        }

        return executeFor(target).build();
    }

    /**
     * Execute the command for the user.
     *
     * @param target the target user.
     */
    protected abstract ResultBuilder executeFor(User target);

}
