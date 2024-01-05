package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.entities.audio.files.AudioFile;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Prev extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);
    public Prev(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
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
