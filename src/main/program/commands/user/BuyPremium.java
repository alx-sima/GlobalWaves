package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.users.User;

@Getter
public final class BuyPremium extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public BuyPremium(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        return resultBuilder.build();
    }
}