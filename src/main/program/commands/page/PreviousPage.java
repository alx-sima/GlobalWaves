package main.program.commands.page;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;

public final class PreviousPage extends NoOutputCommand {

    public PreviousPage(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        if (!caller.getPageHistory().undo()) {
            return "There are no pages left to go back.";
        }

        return "The user " + user + " has navigated successfully to the previous page.";
    }
}

