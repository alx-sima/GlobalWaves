package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.program.Player;
import main.entities.users.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class PlayPause extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    public PlayPause(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
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

        if (player.getQueue() == null) {
            return resultBuilder.withMessage(
                "Please load a source before attempting to pause or resume playback.");
        }

        boolean willPause = !player.isPaused();
        player.setPaused(willPause, timestamp);

        if (willPause) {
            resultBuilder.withMessage("Playback paused successfully.");
        } else {
            resultBuilder.withMessage("Playback resumed successfully.");
        }
        return resultBuilder;
    }
}
