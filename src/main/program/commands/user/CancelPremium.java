package main.program.commands.user;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.users.User;

@Getter
public final class CancelPremium extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public CancelPremium(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        if (!caller.isPremium()) {
            return resultBuilder.returnMessage(user + " is not a premium user.");
        }

        caller.setPremium(false);
        caller.splitPremiumMoney(timestamp);
        return resultBuilder.returnMessage(user + " cancelled the subscription successfully.");
    }
}
