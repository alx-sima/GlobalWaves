package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class PlayPause extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        Player player = caller.getPlayer();
        player.updateTime(timestamp);

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
