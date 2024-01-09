package main.program.commands.user.premium;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;

@Getter
public final class CancelPremium extends NoOutputCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public CancelPremium(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        if (!caller.isPremium()) {
            return user + " is not a premium user.";
        }

        caller.getPlayer().updateTime(timestamp);
        caller.setPremium(false);

        caller.splitPremiumMoney();
        return user + " cancelled the subscription successfully.";
    }
}
