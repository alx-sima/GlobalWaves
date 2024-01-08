package main.program.commands.user.premium;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class BuyPremium extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public BuyPremium(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        if (caller.isPremium()) {
            return resultBuilder.returnMessage(user + " is already a premium user.");
        }

        caller.getPlayer().updateTime(timestamp);
        caller.setPremium(true);

        return resultBuilder.returnMessage(user + " bought the subscription successfully.");
    }
}
