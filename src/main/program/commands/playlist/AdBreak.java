package main.program.commands.playlist;

import fileio.input.commands.AdBreakInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.interactions.Player;

public final class AdBreak extends NoOutputCommand {

    private final double price;

    public AdBreak(final AdBreakInput input) {
        super(input);
        price = input.getPrice();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        Player player = new RequireUserOnline(user).check().getPlayer();

        if (player.getPlayingAt(timestamp) == null) {
            return user + " is not playing any music.";
        }

        player.getQueue().pushAd(price);
        return "Ad inserted successfully.";
    }
}
