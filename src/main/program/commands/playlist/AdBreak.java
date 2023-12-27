package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class AdBreak extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public AdBreak(final CommandInput input) {
        super(input);
    }


    @Override
    protected ResultBuilder execute(final User caller) {
        return resultBuilder;
    }
}
