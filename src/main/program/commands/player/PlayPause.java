package main.program.commands.player;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class PlayPause extends NoOutputCommand {

    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();

        Player player = caller.getPlayer();
        player.updateTime(timestamp);

        if (player.getQueue() == null) {
            return
                "Please load a source before attempting to pause or resume playback.";
        }

        boolean willPause = !player.isPaused();
        player.setPaused(willPause, timestamp);

        if (willPause) {
            return "Playback paused successfully.";
        }
        return "Playback resumed successfully.";
    }
}
