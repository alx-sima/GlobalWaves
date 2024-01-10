package main.program.commands.user.premium;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;

public final class BuyPremium extends NoOutputCommand {

    public BuyPremium(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        if (caller.isPremium()) {
            return user + " is already a premium user.";
        }

        caller.getPlayer().updateTime(timestamp);
        caller.setPremium(true);

        return user + " bought the subscription successfully.";
    }
}
