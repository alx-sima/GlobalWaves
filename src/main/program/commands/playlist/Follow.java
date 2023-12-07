package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
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
    protected CommandResult executeWhenOnline() {
        Program program = Program.getInstance();
        User caller = getCaller();
        Searchable selected = program.getSearchbar().consumeSelectedResult();

        if (selected == null) {
            return new MessageResult(this,
                "Please select a source before following or unfollowing.");
        }

        Playlist playlist = selected.getPlaylist();
        if (playlist == null) {
            return new MessageResult(this, "The selected source is not a playlist.");
        }

        String playlistOwner = playlist.getUser().getUsername();
        if (getUser().equals(playlistOwner)) {
            return new MessageResult(this, "You cannot follow or unfollow your own playlist.");
        }

        if (caller.follow(playlist)) {
            return new MessageResult(this, "Playlist followed successfully.");
        }

        return new MessageResult(this, "Playlist unfollowed successfully.");
    }
}
