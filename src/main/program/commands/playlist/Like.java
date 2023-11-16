package main.program.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.files.Song;
import main.audio.queues.Queue;
import main.audio.queues.SongVisitor;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class Like extends Command {

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Queue queue = instance.getPlayer().getQueue();

        if (queue == null) {
            return new MessageResult(this, "Please load a source before liking or unliking.");
        }

        SongVisitor visitor = new SongVisitor();
        queue.accept(visitor);

        Song song = visitor.getCurrentSong();
        if (song == null) {
            return new MessageResult(this, "Loaded source is not a song.");
        }

        if (user.like(song)) {
            return new MessageResult(this, "Like registered successfully.");
        }
        return new MessageResult(this, "Unlike registered successfully.");
    }
}
