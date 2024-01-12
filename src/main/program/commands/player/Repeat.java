package main.program.commands.player;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequirePlaying;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.repetition.RepeatMode;

public final class Repeat extends NoOutputCommand {

    private static final String NOT_PLAYING_ERROR = "Please load a source before setting the repeat status.";

    public Repeat(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        Queue queue = new RequirePlaying(user, timestamp, NOT_PLAYING_ERROR).check();
        queue.changeRepeatMode();

        RepeatMode newMode = queue.getRepeatMode();
        return "Repeat mode changed to " + newMode.toString().toLowerCase() + ".";
    }
}
