package main.program.commands.player;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.files.AudioFile;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class Prev extends NoOutputCommand {

    public Prev(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Player player = caller.getPlayer();
        Queue queue = player.getQueue();
        player.updateTime(timestamp);

        if (queue == null) {
            return "Please load a source before returning to the previous track.";
        }

        AudioFile prevFile = queue.prev();
        player.setPaused(false, timestamp);
        if (prevFile == null) {
            return "There is no previous track to return to.";
        }
        return "Returned to previous track successfully. The current track is " + prevFile.getName()
            + ".";
    }
}
