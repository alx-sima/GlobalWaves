package main.program.commands.dependencies;

import fileio.output.CommandResult;
import fileio.output.ResultBuilder;
import main.program.Program;
import main.program.commands.DependentCommand;

public final class IsArtistDependency extends CommandDependency {

    public IsArtistDependency(final DependentCommand command, final ResultBuilder resultBuilder) {
        super(command, resultBuilder);
    }

    @Override
    public CommandResult checkDependencies() {
        ExistsUserDependency dependency = new ExistsUserDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        Program program = Program.getInstance();
        if (!program.getDatabase().getArtists().contains(getCaller())) {
            return resultBuilder.withMessage(user + " is not an artist.");
        }
        return chainedCommand.executeIfDependenciesMet();
    }
}
