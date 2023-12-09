package main.program.commands.dependencies;

import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import main.program.Program;
import main.entities.users.UserDatabase;
import main.program.commands.DependentCommand;

public final class ExistsUserDependency extends CommandDependency {

    public ExistsUserDependency(final DependentCommand command, final ResultBuilder resultBuilder) {
        super(command, resultBuilder);
    }

    @Override
    public CommandResult checkDependencies() {
        return executeIfDependenciesMet().build();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Program program = Program.getInstance();
        UserDatabase database = program.getDatabase();

        if (!database.existsUser(user)) {
            return resultBuilder.withMessage("The username " + user + " doesn't exist.");
        }
        return chainedCommand.executeIfDependenciesMet();
    }
}
