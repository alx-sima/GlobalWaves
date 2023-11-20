package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.MessageResult;
import main.audio.queues.Queue;
import main.audio.queues.ShuffleVisitor;
import main.program.Program;
import main.program.Player;
import main.program.User;
import main.program.commands.Command;
import fileio.output.CommandResult;

public final class Shuffle extends Command {

    private final int seed;

    public Shuffle(final CommandInput input) {
        super(input);
        seed = input.getSeed();
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Player player = user.getPlayer();
        player.updateTime(getTimestamp());
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
            player.updateTime(getTimestamp());
            return new MessageResult(this, "Shuffle function deactivated successfully.");
        }
        ShuffleVisitor visitor = new ShuffleVisitor(seed);
        queue.accept(visitor);
        return new MessageResult(this, "Shuffle function activated successfully.");
    }
}
