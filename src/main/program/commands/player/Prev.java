package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.files.AudioFile;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

@Getter
public final class Prev extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);
    public Prev(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Player player = caller.getPlayer();
        Queue queue = player.getQueue();
        player.updateTime(timestamp);

        if (queue == null) {
            return resultBuilder.returnMessage(
                "Please load a source before returning to the previous track.");
        }

        AudioFile prevFile = queue.prev();
        player.setPaused(false, timestamp);
        if (prevFile == null) {
            return resultBuilder.returnMessage("There is no previous track to return to.");
        }
        return resultBuilder.returnMessage(
            "Returned to previous track successfully. The current track is " + prevFile.getName()
                + ".");
    }
}
