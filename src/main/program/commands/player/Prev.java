package main.program.commands.player;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.files.AudioFile;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Prev extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);
    public Prev(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        Player player = caller.getPlayer();
        Queue queue = player.getQueue();
        player.updateTime(timestamp);

        if (queue == null) {
            return resultBuilder.withMessage(
                "Please load a source before returning to the previous track.");
        }

        AudioFile prevFile = queue.prev();
        player.setPaused(false, timestamp);
        if (prevFile == null) {
            return resultBuilder.withMessage("There is no previous track to return to.");
        }
        return resultBuilder.withMessage(
            "Returned to previous track successfully. The current track is " + prevFile.getName()
                + ".");
    }
}
