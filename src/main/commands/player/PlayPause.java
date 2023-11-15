package main.commands.player;

import fileio.input.CommandInput;
import fileio.output.MessageResult;
import main.Program;
import main.audio.Player;
import main.commands.Command;
import fileio.output.CommandResult;

public final class PlayPause extends Command {

    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        Player player = instance.getPlayer();

//        if (player.getQueue().isEmpty()) {
//            return new Result(this, "Please load a source before attempting to pause or resume "
//              + "playback");
//        }

        if (player.togglePaused(getTimestamp())) {
            return new MessageResult(this, "Playback paused successfully.");
        }
        return new MessageResult(this, "Playback resumed successfully.");
    }
}
