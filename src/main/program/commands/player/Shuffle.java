package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.MessageResult;
import main.program.Program;
import main.program.Player;
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
        Player player = instance.getPlayer();

        if (player == null) {
            return new MessageResult(this,
                "Please load a source before using the shuffle function.");
        }

//        if (player.getQueue().size() == 1) {
//            return new MessageResult(this, "The loaded source is not a playlist.");
//        }

//        if (player.toggleShuffled()) {
//            player.setShuffleSeed(seed);
//            return new MessageResult(this, "Shuffle function activated successfully");
//        }
        return new MessageResult(this, "Shuffle function deactivated successfully.");
    }
}
