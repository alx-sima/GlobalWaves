package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.MessageResult;
import fileio.output.MessageResult.Builder;
import lombok.Getter;
import main.program.commands.Command;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.users.User;

@Getter
public final class Follow extends Command {

    private final MessageResult.Builder resultBuilder = new Builder(this);

    public Follow(final CommandInput input) {
        super(input);
    }

    @Override
    protected MessageResult execute() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        SearchableAudio selected = caller.getSearchbar().getSelectedAudioSource();

        if (selected == null) {
            return resultBuilder.returnMessage(
                "Please select a source before following or unfollowing.");
        }

        Playlist playlist = selected.getPlaylist();
        if (playlist == null) {
            return resultBuilder.returnMessage("The selected source is not a playlist.");
        }

        String playlistOwner = playlist.getUser().getUsername();
        if (getUser().equals(playlistOwner)) {
            return resultBuilder.returnMessage("You cannot follow or unfollow your own playlist.");
        }

        if (caller.follow(playlist)) {
            return resultBuilder.returnMessage("Playlist followed successfully.");
        }
        return resultBuilder.returnMessage("Playlist unfollowed successfully.");
    }
}
