package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.users.User;

@Getter
public final class LoadRecommendations extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public LoadRecommendations(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        return resultBuilder;
    }
}
