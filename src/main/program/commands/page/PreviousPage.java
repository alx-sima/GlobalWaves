package main.program.commands.page;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class PreviousPage extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public PreviousPage(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        return resultBuilder.build();
    }
}

