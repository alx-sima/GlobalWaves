package main.program.commands.player;

import fileio.input.commands.ShuffleInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.audio.queues.Queue;
import main.program.Player;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Shuffle extends OnlineCommand {

    private final int seed;

    public Shuffle(final ShuffleInput input) {
        super(input);
        seed = input.getSeed();
    }

    @Override
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        User caller = getCaller();
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            resultBuilder.withMessage("Please load a source before using the shuffle function.");
            return resultBuilder.build();
        }

        if (!queue.isShuffle()) {
            resultBuilder.withMessage("The loaded source is not a playlist.");
            return resultBuilder.build();
        }

        if (queue.isShuffled()) {
            queue.disableShuffle();
            player.updateTime(timestamp);
            resultBuilder.withMessage("Shuffle function deactivated successfully.");
        } else {
            queue.enableShuffle(seed);
            resultBuilder.withMessage("Shuffle function activated successfully.");
        }
        return resultBuilder.build();
    }
}
