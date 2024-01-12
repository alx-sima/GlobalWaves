package main.program.commands.playlist;

import fileio.input.commands.CommandInput;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.NoOutputCommand;
import main.program.commands.exceptions.InvalidOperation;
import main.program.commands.requirements.RequireUserOnline;
import main.program.entities.users.User;

public final class CreatePlaylist extends NoOutputCommand {

    private final String playListName;

    public CreatePlaylist(final Input input) {
        super(input);
        playListName = input.getPlaylistName();
    }

    @Override
    protected String executeNoOutput() throws InvalidOperation {
        User caller = new RequireUserOnline(user).check();
        if (caller.createPlaylist(playListName, timestamp)) {
            return "Playlist created successfully.";
        }
        return "A playlist with the same name already exists.";
    }

    @Getter
    @Setter
    public static final class Input extends CommandInput {

        private String playlistName;

        @Override
        public Command createCommand() {
            return new CreatePlaylist(this);
        }
    }
}
