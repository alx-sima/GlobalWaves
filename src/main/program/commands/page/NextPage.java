package main.program.commands.page;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;

public final class NextPage extends NoOutputCommand {

    public NextPage(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        if (!caller.getPageHistory().redo()) {
            return "There are no pages left to go forward.";
        }

        return "The user " + user + " has navigated successfully to the next page.";
    }
}
