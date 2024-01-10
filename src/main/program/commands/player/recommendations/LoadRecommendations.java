package main.program.commands.player.recommendations;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class LoadRecommendations extends NoOutputCommand {

    public LoadRecommendations(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        SearchableAudio queue = caller.getRecommendations().getRecommendation();
        if (queue == null) {
            return "No recommendations available.";
        }

        Player player = caller.getPlayer();
        player.addQueue(caller, queue, timestamp);
        return "Playback loaded successfully.";
    }
}
