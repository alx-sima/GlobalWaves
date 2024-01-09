package main.program.commands.page;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;

@Getter
public final class PrintCurrentPage extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public PrintCurrentPage(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        return resultBuilder.returnMessage(caller.getCurrentPage().printPage());
    }
}
