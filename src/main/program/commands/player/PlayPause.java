package main.program.commands.player;

import fileio.input.CommandInput;
import fileio.output.MessageResult;
import main.program.Program;
import main.program.Player;
import main.program.User;
import main.program.commands.Command;
import fileio.output.CommandResult;

public final class PlayPause extends Command {

    public PlayPause(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Player player = user.getPlayer();
        if (player.getQueue() == null) {
            return new MessageResult(this,
                "Please load a source before attempting to pause or resume playback.");
        }

        if (player.togglePaused(getTimestamp())) {
            return new MessageResult(this, "Playback paused successfully.");
        }
        return new MessageResult(this, "Playback resumed successfully.");
    }
}
