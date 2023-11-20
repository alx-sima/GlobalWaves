package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.playlist.CreatePlaylist;

@Getter
@Setter
public final class PlaylistCreateInput extends CommandInput {

    private String playlistName;

    public PlaylistCreateInput() {
    }

    @Override
    public Command createCommand() {
        if (command.equals("createPlaylist")) {
            return new CreatePlaylist(this);
        }

        return null;
    }
}
