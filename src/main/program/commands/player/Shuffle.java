package main.program.commands.player;

import fileio.input.commands.ShuffleInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.queues.Queue;
import main.program.Player;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

public final class Shuffle extends OnlineUserCommand {

    @Getter
    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);
    private final int seed;

    public Shuffle(final ShuffleInput input) {
        super(input);
        seed = input.getSeed();
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        Player player = caller.getPlayer();
        player.updateTime(timestamp);
        Queue queue = player.getQueue();

        if (queue == null) {
            return resultBuilder.withMessage(
                "Please load a source before using the shuffle function.");
        }

        if (!queue.isShuffle()) {
            return resultBuilder.withMessage("The loaded source is not a playlist or an album.");
        }

        if (queue.isShuffled()) {
            queue.disableShuffle();
            player.updateTime(timestamp);
            resultBuilder.withMessage("Shuffle function deactivated successfully.");
        } else {
            queue.enableShuffle(seed);
            resultBuilder.withMessage("Shuffle function activated successfully.");
        }
        return resultBuilder;
    }
}
