package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
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
    public final MessageResult execute() {
        User target = UserDatabase.getInstance().getUser(user);
        if (target == null) {
            return getResultBuilder().returnMessage("The username " + user + " doesn't exist.");
        }

        return executeFor(target);
    }

    /**
     * Execute the command for the user.
     *
     * @param target the target user.
     */
    protected abstract MessageResult executeFor(User target);

}
