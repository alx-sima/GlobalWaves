package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.program.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.ExistsUserDependency;

public final class SwitchConnectionStatus extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    public SwitchConnectionStatus(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
    }

    @Override
    public CommandResult checkDependencies() {
        ExistsUserDependency dependency = new ExistsUserDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        User caller = getCaller();
        boolean newOnlineStatus = !caller.isOnline();
        caller.setOnline(newOnlineStatus, timestamp);

        return resultBuilder.withMessage(user + " has changed status successfully.");
    }
}
