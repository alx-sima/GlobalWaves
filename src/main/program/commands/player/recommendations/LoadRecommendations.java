package main.program.commands.player.recommendations;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

@Getter
public final class LoadRecommendations extends Command {

    private final Builder resultBuilder = new Builder(this);

    public LoadRecommendations(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        SearchableAudio queue = caller.getRecommendations().getRecommendation();
        if (queue == null) {
            return resultBuilder.returnMessage("No recommendations available.");
        }

        Player player = caller.getPlayer();
        player.addQueue(caller, queue, timestamp);
        return resultBuilder.returnMessage("Playback loaded successfully.");
    }
}
