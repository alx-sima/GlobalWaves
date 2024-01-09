package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.WrappedResult;
import fileio.output.WrappedResult.Builder;
import fileio.output.wrapped.WrappedOutput;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.ExistsUser;
import main.program.databases.UserDatabase;
import main.program.entities.users.User;

@Getter
public final class Wrapped extends Command {

    private final WrappedResult.Builder resultBuilder = new Builder(this);

    public Wrapped(final CommandInput input) {
        super(input);
    }

    @Override
    protected CommandResult execute() throws InvalidOperation {
        User target = new ExistsUser(user).check();

        // Update everyone's player in case they affect the target's wrapped.
        for (User u : UserDatabase.getInstance().getUsers()) {
            u.getPlayer().updateTime(timestamp);
        }

        WrappedOutput stats = target.getWrapped();
        String wrappedReturnMessage = stats.returnMessage();
        if (wrappedReturnMessage != null) {
            return resultBuilder.returnMessage(String.format(wrappedReturnMessage, user));
        }

        return resultBuilder.result(stats).build();
    }
}
