package main.program.commands.player;

import fileio.input.commands.ShuffleInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.Shuffler;
import main.program.entities.audio.queues.visitors.ShuffleVisitor;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class Shuffle extends NoOutputCommand {

    private final int seed;

    public Shuffle(final ShuffleInput input) {
        super(input);
        seed = input.getSeed();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            return
                "Please load a source before using the shuffle function.";
        }

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
        player.updateTime(timestamp);
        return "Shuffle function deactivated successfully.";
    }
}
