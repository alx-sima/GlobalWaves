package main.program.commands.player;

import fileio.input.commands.ShuffleInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
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
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            return new MessageResult(this,
                "Please load a source before using the shuffle function.");
        }

        if (!queue.isShuffle()) {
            return new MessageResult(this, "The loaded source is not a playlist.");
        }

        if (queue.isShuffled()) {
            queue.disableShuffle();
            player.updateTime(timestamp);
            return new MessageResult(this, "Shuffle function deactivated successfully.");
        }
        queue.enableShuffle(seed);
        return new MessageResult(this, "Shuffle function activated successfully.");
    }
}
