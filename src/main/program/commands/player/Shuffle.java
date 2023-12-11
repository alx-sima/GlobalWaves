package main.program.commands.player;

import fileio.input.commands.ShuffleInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.audio.queues.Queue;
import main.program.Player;
import main.entities.users.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Shuffle extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    private final int seed;

    public Shuffle(final ShuffleInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        seed = input.getSeed();
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        User caller = getCaller();
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
