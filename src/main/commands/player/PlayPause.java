package main.commands.player;

import fileio.input.CommandInput;
import main.Program;
import main.audio.Player;
import main.commands.Command;
import main.commands.Result;

public final class PlayPause extends Command {
    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    public Result execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();

//        if (player.getQueue().isEmpty()) {
//            return new Result(this, "Please load a source before attempting to pause or resume " + "playback");
//        }

        if (player.togglePaused(getTimestamp())) {
            return new Result(this, "Playback paused successfully.");
        }
        return new Result(this, "Playback resumed successfully.");
    }
}
