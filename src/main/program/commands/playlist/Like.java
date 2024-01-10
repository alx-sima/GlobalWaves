package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.Queue;
import main.program.entities.users.User;
import main.program.entities.users.interactions.Player;

public final class Like extends NoOutputCommand {

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        Player player = caller.getPlayer();
        Queue queue = player.getQueue();
        player.updateTime(timestamp);

        if (queue == null) {
            return "Please load a source before liking or unliking.";
        }

        Song song = queue.getCurrentSong();
        if (song == null) {
            return "Loaded source is not a song";
        }

        if (caller.like(song)) {
            return "Like registered successfully.";
        }
        return "Unlike registered successfully.";
    }
}
