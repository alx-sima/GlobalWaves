package main.program.commands.player;

import fileio.input.commands.CommandInput;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequirePlaying;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.Shuffler;
import main.program.entities.audio.queues.visitors.ShuffleVisitor;
import main.program.entities.users.User;

public final class Shuffle extends NoOutputCommand {

    private static final String NOT_PLAYING_ERROR =
        "Please load a source before using the shuffle function.";

    private final int seed;

    public Shuffle(final Input input) {
        super(input);
        seed = input.getSeed();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        RequirePlaying requirement = new RequirePlaying(user, timestamp, NOT_PLAYING_ERROR);
        Queue queue = requirement.check();

        ShuffleVisitor visitor = new ShuffleVisitor(seed);
        queue.accept(visitor);

        if (!visitor.isShuffleable()) {
            return "The loaded source is not a playlist or an album.";
        }

        Shuffler shuffler = visitor.getShuffler();
        if (shuffler != null) {
            queue.enableShuffle(shuffler);
            return "Shuffle function activated successfully.";
        }

        queue.disableShuffle();

        User caller = requirement.getCaller();
        caller.getPlayer().updateTime(timestamp);
        return "Shuffle function deactivated successfully.";
    }

    @Getter
    @Setter
    public static final class Input extends CommandInput {

        private int seed;

        @Override
        public Command createCommand() {
            return new Shuffle(this);
        }
    }
}
