package main.program.commands.player;

import fileio.input.commands.CommandInput;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.interactions.Player;

public final class AdBreak extends NoOutputCommand {

    private final double price;

    public AdBreak(final Input input) {
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

    @Getter
    @Setter
    public static final class Input extends CommandInput {

        private double price;

        @Override
        public Command createCommand() {
            return new AdBreak(this);
        }
    }
}
