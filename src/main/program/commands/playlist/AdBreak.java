package main.program.commands.playlist;

import fileio.input.commands.AdBreakInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.commands.user.OnlineUserCommand;
import main.program.entities.users.interactions.Player;

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
        Player player = caller.getPlayer();

        if (player.getPlayingAt(timestamp) == null) {
            return resultBuilder.returnMessage(user + " is not playing any music.");
        }

        player.getQueue().pushAd(price);
        return resultBuilder.returnMessage("Ad inserted successfully.");
    }
}
