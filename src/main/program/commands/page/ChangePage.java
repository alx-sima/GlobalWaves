package main.program.commands.page;

import fileio.input.commands.ChangePageInput;
import fileio.output.CommandResult;
import main.program.commands.OnlineCommand;

public final class ChangePage extends OnlineCommand {

    private final String nextPage;

    public ChangePage(final ChangePageInput input) {
        super(input);
        nextPage = input.getNextPage();
    }

    @Override
    protected CommandResult executeWhenOnline() {
        // TODO
        return null;
    }
}
