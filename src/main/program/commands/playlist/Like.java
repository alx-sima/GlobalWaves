package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.files.Song;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Like extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        Player player = caller.getPlayer();
        Queue queue = player.getQueue();
        player.updateTime(timestamp);

        if (queue == null) {
            return resultBuilder.withMessage("Please load a source before liking or unliking.");
        }

        Song song = queue.getCurrentSong();
        if (song == null) {
            return resultBuilder.withMessage("Loaded source is not a song");
        }

        if (caller.like(song)) {
            resultBuilder.withMessage("Like registered successfully.");
        } else {
            resultBuilder.withMessage("Unlike registered successfully.");
        }
        return resultBuilder;
    }
}
