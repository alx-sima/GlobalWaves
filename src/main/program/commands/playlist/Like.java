package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.audio.files.Song;
import main.entities.audio.queues.Queue;
import main.entities.users.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Like extends DependentCommand {

    private final MessageResultBuilder resultBuilder;
    public Like(final CommandInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        User caller = getCaller();
        Queue queue = caller.getPlayer().getQueue();
        caller.getPlayer().updateTime(timestamp);

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
