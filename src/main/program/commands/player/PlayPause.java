package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.program.Player;
import main.program.User;
import main.program.commands.Command;

public final class PlayPause extends Command {

    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        User callee = getCallee();
        Player player = callee.getPlayer();
        if (player.getQueue() == null) {
            return new MessageResult(this,
                "Please load a source before attempting to pause or resume playback.");
        }

        if (player.togglePaused(timestamp)) {
            return new MessageResult(this, "Playback paused successfully.");
        }
        return new MessageResult(this, "Playback resumed successfully.");
    }
}
