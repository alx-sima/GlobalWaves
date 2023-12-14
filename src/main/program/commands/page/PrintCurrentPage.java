package main.program.commands.page;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class PrintCurrentPage extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder();

    public PrintCurrentPage(final CommandInput input) {
        super(input);
        resultBuilder.withCommand(this);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        return resultBuilder.withMessage(caller.getCurrentPage().printPage());
    }
}
