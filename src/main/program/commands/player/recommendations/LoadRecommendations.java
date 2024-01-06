package main.program.commands.player.recommendations;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.audio.SearchableAudio;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class LoadRecommendations extends OnlineUserCommand {

    private final Builder resultBuilder = new Builder(this);

    public LoadRecommendations(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        SearchableAudio queue = caller.getRecommendations().getRecommendation();
        if (queue == null) {
            return resultBuilder.returnMessage("No recommendations available.");
        }

        Player player = caller.getPlayer();
        player.addQueue(caller, queue, timestamp);
        return resultBuilder.returnMessage("Playback loaded successfully.");
    }
}
