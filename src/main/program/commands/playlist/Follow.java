package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResultBuilder;
import main.audio.Searchable;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.User;
import main.program.commands.OnlineCommand;

public final class Follow extends OnlineCommand {

    public Follow(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResultBuilder createResultBuilder() {
        return new MessageResultBuilder(this);
    }

    @Override
    protected CommandResult executeWhenOnline() {
        MessageResultBuilder resultBuilder = createResultBuilder();

        Program program = Program.getInstance();
        User caller = getCaller();
        Searchable selected = program.getSearchbar().consumeSelectedResult();

        if (selected == null) {
            resultBuilder.withMessage("Please select a source before following or unfollowing.");
            return resultBuilder.build();
        }

        Playlist playlist = selected.getPlaylist();
        if (playlist == null) {
            resultBuilder.withMessage("The selected source is not a playlist.");
            return resultBuilder.build();
        }

        String playlistOwner = playlist.getUser().getUsername();
        if (getUser().equals(playlistOwner)) {
            resultBuilder.withMessage("You cannot follow or unfollow your own playlist.");
            return resultBuilder.build();
        }

        if (caller.follow(playlist)) {
            resultBuilder.withMessage("Playlist followed successfully.");
        } else {
            resultBuilder.withMessage("Playlist unfollowed successfully.");
        }
        return resultBuilder.build();
    }
}
