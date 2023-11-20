package main.program.commands.playlist;

import fileio.input.CommandInput;
import fileio.output.CommandResult;
import fileio.output.MessageResult;
import main.audio.Searchable;
import main.audio.collections.Playlist;
import main.program.Program;
import main.program.User;
import main.program.commands.Command;

public final class Follow extends Command {

    public Follow(CommandInput input) {
        super(input);
    }

    @Override
    public CommandResult execute() {
        Program instance = Program.getInstance();
        User user = instance.getUsers().get(getUser());
        Searchable selected = instance.getSelectedResult();

        if (selected == null) {
            return new MessageResult(this,
                "Please select a source before following or unfollowing.");
        }

        Playlist playlist = selected.getPlaylist();
        instance.setSelectedResult(null);
        if (playlist == null) {
            return new MessageResult(this, "The selected source is not a playlist.");
        }

        if (getUser().equals(playlist.getUser().getUsername())) {
            return new MessageResult(this, "You cannot follow or unfollow your own playlist.");
        }

        if (user.follow(playlist)) {
            return new MessageResult(this, "Playlist followed successfully.");
        }

        return new MessageResult(this, "Playlist unfollowed successfully.");
    }
}