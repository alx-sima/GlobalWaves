package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.audio.Searchable;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class Follow extends DependentCommand {

    private final MessageResultBuilder resultBuilder;

    public Follow(final CommandInput input) {
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
        Program program = Program.getInstance();
        User caller = getCaller();
        Searchable selected = program.getSearchbar().consumeSelectedResult();

        if (selected == null) {
            return resultBuilder.withMessage(
                "Please select a source before following or unfollowing.");
        }

        Playlist playlist = selected.getPlaylist();
        if (playlist == null) {
            return resultBuilder.withMessage("The selected source is not a playlist.");
        }

        String playlistOwner = playlist.getUser().getUsername();
        if (getUser().equals(playlistOwner)) {
            return resultBuilder.withMessage("You cannot follow or unfollow your own playlist.");
        }

        if (caller.follow(playlist)) {
            return resultBuilder.withMessage("Playlist followed successfully.");
        }
        return resultBuilder.withMessage("Playlist unfollowed successfully.");
    }
}
