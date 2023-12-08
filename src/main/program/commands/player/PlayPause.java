package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.program.Player;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class PlayPause extends OnlineCommand {

    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        User caller = getCaller();
        Player player = caller.getPlayer();

        if (player.getQueue() == null) {
            resultBuilder.withMessage(
                "Please load a source before attempting to pause or resume playback.");
            return resultBuilder.build();
        }

        boolean willPause = !player.isPaused();
        player.setPaused(willPause, timestamp);

        if (willPause) {
            resultBuilder.withMessage("Playback paused successfully.");
        } else {
            resultBuilder.withMessage("Playback resumed successfully.");
        }
        return resultBuilder.build();
    }
}
