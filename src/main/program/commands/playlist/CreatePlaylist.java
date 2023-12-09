package main.program.commands.playlist;

import fileio.input.commands.PlaylistCreateInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import fileio.output.ResultBuilder;
import main.entities.users.User;
import main.program.commands.DependentCommand;
import main.program.commands.dependencies.OnlineUserDependency;

public final class CreatePlaylist extends DependentCommand {

    private final ResultBuilder resultBuilder;
    private final String playListName;

    public CreatePlaylist(final PlaylistCreateInput input) {
        super(input);
        resultBuilder = new MessageResultBuilder(this);
        playListName = input.getPlaylistName();
    }

    @Override
    public CommandResult checkDependencies() {
        OnlineUserDependency dependency = new OnlineUserDependency(this, resultBuilder);
        return dependency.execute();
    }

    @Override
    public ResultBuilder executeIfDependenciesMet() {
        User caller = getCaller();

        if (caller.createPlaylist(playListName, timestamp)) {
            resultBuilder.withMessage("Playlist created successfully.");
        } else {
            resultBuilder.withMessage("A playlist with the same name already exists.");
        }
        return resultBuilder;
    }
}
