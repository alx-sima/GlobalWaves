package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import main.program.entities.users.User;

/**
 * A command that can be executed only by online users.
 */
public abstract class OnlineUserCommand extends UserCommand {

    protected OnlineUserCommand(final CommandInput input) {
        super(input);
    }

    @Override
    public final MessageResult executeFor(final User target) {
        if (!target.isOnline()) {
            return getResultBuilder().returnMessage(user + " is offline.");
        }

        return execute(target);
    }

    /**
     * Executes the command for the given user.
     *
     * @param caller the user specified by the command.
     * @return the result of the command.
     */
    protected abstract MessageResult execute(User caller);
}
