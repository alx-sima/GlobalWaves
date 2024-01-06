package main.program.commands.stats;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.WrappedResult;
import fileio.output.WrappedResult.Builder;
import fileio.output.wrapped.WrappedOutput;
import lombok.Getter;
import main.entities.users.User;
import main.entities.users.UserDatabase;
import main.program.commands.user.UserCommand;

@Getter
public final class Wrapped extends UserCommand {

    private final WrappedResult.Builder resultBuilder = new Builder(this);

    public Wrapped(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult executeFor(final User target) {
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
