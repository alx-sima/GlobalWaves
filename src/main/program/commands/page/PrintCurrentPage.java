package main.program.commands.page;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class PrintCurrentPage extends DependentCommand {

    private final MessageResultBuilder resultBuilder;

    public PrintCurrentPage(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.checkDependencies();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        return resultBuilder.withMessage(getCaller().getCurrentPage().printPageOfUser(getCaller()));
    }
}
