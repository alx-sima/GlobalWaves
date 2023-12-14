package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import fileio.output.builders.ResultBuilder;
import lombok.Getter;
import main.entities.audio.SearchableAudio;
import main.entities.audio.collections.Playlist;
import main.entities.users.User;
import main.program.commands.user.OnlineUserCommand;

@Getter
public final class Follow extends OnlineUserCommand {

    private final ResultBuilder resultBuilder = new ResultBuilder().withCommand(this);

    public Follow(final CommandInput input) {
        super(input);
    }

    @Override
    protected ResultBuilder execute(final User caller) {
        SearchableAudio selected = caller.getSearchbar().getSelectedAudioSource();

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
