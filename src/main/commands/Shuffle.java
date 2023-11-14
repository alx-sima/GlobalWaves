package main.commands;

import main.Program;
import main.audio.Player;

public final class Shuffle extends Command {
    private final int seed;

    public Shuffle(String command, String user, int timestamp, int seed) {
        super(command, user, timestamp);
        this.seed = seed;
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();

        if (player == null) {
            return new Result(this, "Please load a source before using the shuffle function.");
        }

        if (player.getQueue().size() == 1) {
            return new Result(this, "The loaded source is not a playlist.");
        }

        if (player.toggleShuffled()) {
            player.setShuffleSeed(seed);
            return new Result(this, "Shuffle function activated successfully");
        }
        return new Result(this, "Shuffle function deactivated successfully.");
    }
}
