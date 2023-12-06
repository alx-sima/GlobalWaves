package main.program.commands.page;

import fileio.input.commands.ChangePageInput;
import fileio.output.CommandResult;
import main.program.commands.Command;

public final class ChangePage extends Command {

    private final String nextPage;

    public ChangePage(final ChangePageInput input) {
        super(input);
        nextPage = input.getNextPage();
    }

    @Override
    public CommandResult execute() {
        // TODO
        return null;
    }
}
