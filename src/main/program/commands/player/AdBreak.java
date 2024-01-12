package main.program.commands.player;

import fileio.input.commands.CommandInput;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequirePlaying;
import main.program.entities.audio.queues.Queue;

public final class AdBreak extends NoOutputCommand {

    private final double price;

    public AdBreak(final Input input) {
        super(input);
        price = input.getPrice();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        Queue queue = new RequirePlaying(user, timestamp,
            user + " is not playing any music.").check();
        queue.pushAd(price);
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
