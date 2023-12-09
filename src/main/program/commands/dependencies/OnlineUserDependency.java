package main.program.commands.dependencies;

import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import main.program.commands.DependentCommand;

public final class OnlineUserDependency extends CommandDependency {

    public OnlineUserDependency(final DependentCommand command, final ResultBuilder resultBuilder) {
        super(command, resultBuilder);
    }

    @Override
    public CommandResult checkDependencies() {
        ExistsUserDependency dependency = new ExistsUserDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        if (!getCaller().isOnline()) {
            return resultBuilder.withMessage(user + " is offline.");
        }
        return chainedCommand.executeIfDependenciesMet();
    }
}
