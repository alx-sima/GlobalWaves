package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class PlayPause extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        Player player = caller.getPlayer();
        player.updateTime(timestamp);

        if (player.getQueue() == null) {
            return resultBuilder.returnMessage(
                "Please load a source before attempting to pause or resume playback.");
        }

        boolean willPause = !player.isPaused();
        player.setPaused(willPause, timestamp);

        if (willPause) {
            return resultBuilder.returnMessage("Playback paused successfully.");
        }
        return resultBuilder.returnMessage("Playback resumed successfully.");
    }
}
