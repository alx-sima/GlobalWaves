package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class AdBreak extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public AdBreak(final CommandInput input) {
        super(input);
    }


    @Override
    protected MessageResult execute(final User caller) {
        return resultBuilder.build();
    }
}
