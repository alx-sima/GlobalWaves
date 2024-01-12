package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequirePlaying;
import main.program.entities.audio.files.Song;
import main.program.entities.audio.queues.Queue;
import main.program.entities.audio.queues.visitors.PlayingSongVisitor;
import main.program.entities.users.User;

public final class Like extends NoOutputCommand {

    private static final String NOT_PLAYING_ERROR =
        "Please load a source before liking or unliking.";

    public Like(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        RequirePlaying requirement = new RequirePlaying(user, timestamp, NOT_PLAYING_ERROR);
        Queue queue = requirement.check();

        PlayingSongVisitor visitor = new PlayingSongVisitor();
        queue.accept(visitor);

        Song currentSong = visitor.getPlayingSong();
        if (currentSong == null) {
            return "Loaded source is not a song";
        }

        User caller = requirement.getCaller();
        if (caller.like(currentSong)) {
            return "Like registered successfully.";
        }
        return "Unlike registered successfully.";
    }
}
