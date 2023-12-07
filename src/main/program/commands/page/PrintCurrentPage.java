package main.program.commands.page;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import main.program.commands.OnlineCommand;

public class PrintCurrentPage extends OnlineCommand {

    public PrintCurrentPage(final CommandInput input) {
        super(input);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        // TODO
        return null;
    }
}
