package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.program.Player;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class PlayPause extends OnlineCommand {

    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        User caller = getCaller();
        Player player = caller.getPlayer();

        if (player.getQueue() == null) {
            return new MessageResult(this,
                "Please load a source before attempting to pause or resume playback.");
        }

        boolean willPause = !player.isPaused();
        player.setPaused(willPause, timestamp);

        if (willPause) {
            return new MessageResult(this, "Playback paused successfully.");
        }
        return new MessageResult(this, "Playback resumed successfully.");
    }
}
