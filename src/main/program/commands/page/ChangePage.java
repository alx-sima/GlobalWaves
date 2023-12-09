package main.program.commands.page;

import fileio.input.commands.ChangePageInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.program.commands.DependentCommand;

public final class ChangePage extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final String nextPage;

    public ChangePage(final ChangePageInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        nextPage = input.getNextPage();
    }

    @Override
    public CommandResult checkDependencies() {
        // TODO
        return null;
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        // TODO
        return null;
    }
}
