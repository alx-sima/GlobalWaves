package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Like extends OnlineUserCommand {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute(final User caller) {
        Player player = caller.getPlayer();
        Queue queue = player.getQueue();
        player.updateTime(timestamp);

        if (queue == null) {
            return resultBuilder.returnMessage("Please load a source before liking or unliking.");
        }

        Song song = queue.getCurrentSong();
        if (song == null) {
            return resultBuilder.returnMessage("Loaded source is not a song");
        }

        if (caller.like(song)) {
            return resultBuilder.returnMessage("Like registered successfully.");
        }
        return resultBuilder.returnMessage("Unlike registered successfully.");
    }
}
