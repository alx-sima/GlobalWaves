package main.program.commands.player;

import fileio.input.commands.ShuffleInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.Shuffler;
import main.program.entities.audio.queues.visitors.ShuffleVisitor;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class Shuffle extends Command {

    @Getter
    private final MessageResult.Builder resultBuilder = new Builder(this);
    private final int seed;

    public Shuffle(final ShuffleInput input) {
        super(input);
        seed = input.getSeed();
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            return resultBuilder.returnMessage(
                "Please load a source before using the shuffle function.");
        }

        ShuffleVisitor visitor = new ShuffleVisitor(seed);
        queue.accept(visitor);

        if (!visitor.isShuffleable()) {
            return resultBuilder.returnMessage("The loaded source is not a playlist or an album.");
        }

        Shuffler shuffler = visitor.getShuffler();
        if (shuffler != null) {
            queue.enableShuffle(shuffler);
            return resultBuilder.returnMessage("Shuffle function activated successfully.");
        }

        queue.disableShuffle();
        player.updateTime(timestamp);
        return resultBuilder.returnMessage("Shuffle function deactivated successfully.");
    }
}
