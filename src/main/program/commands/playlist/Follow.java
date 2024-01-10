package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.audio.SearchableAudio;
import main.program.entities.audio.collections.Playlist;
import main.program.entities.users.User;

public final class Follow extends NoOutputCommand {

    public Follow(final CommandInput input) {
        super(input);
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        SearchableAudio selected = caller.getSearchbar().getSelectedAudioSource();

        if (selected == null) {
            return "Please select a source before following or unfollowing.";
        }

        Playlist playlist = selected.getPlaylist();
        if (playlist == null) {
            return "The selected source is not a playlist.";
        }

        String playlistOwner = playlist.getUser().getUsername();
        if (getUser().equals(playlistOwner)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (caller.follow(playlist)) {
            return "Playlist followed successfully.";
        }
        return "Playlist unfollowed successfully.";
    }
}
