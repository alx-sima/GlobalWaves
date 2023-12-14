package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import main.entities.users.User;

public abstract class OnlineUserCommand extends UserCommand {

    protected OnlineUserCommand(final CommandInput input) {
        super(input);
    }

    @Override
    public final ResultBuilder executeFor(final User target) {
        if (!target.isOnline()) {
            return getResultBuilder().withMessage(user + " is offline.");
        }

        return execute(target);
    }

    /**
     * Executes the command for the given user.
     *
     * @param caller the user specified by the command.
     * @return the result of the command.
     */
    protected abstract ResultBuilder execute(User caller);
}
