package main.program.commands.playlist;

import fileio.input.commands.AdBreakInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class AdBreak extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final double price;

    public AdBreak(final AdBreakInput input) {
        super(input);
        price = input.getPrice();
    }


    @Override
    protected MessageResult execute(final User caller) {
        if (caller.getPlayer().getPlayingAt(timestamp) == null) {
            return resultBuilder.returnMessage(user + " is not playing any music.");
        }

        caller.getPlayer().getQueue().pushAd(price);
        return resultBuilder.returnMessage("Ad inserted successfully.");
    }
}
