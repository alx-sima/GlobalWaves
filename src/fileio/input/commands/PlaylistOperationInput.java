package fileio.input.commands;

import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.playlist.AddRemoveInPlaylist;
import main.program.commands.playlist.SwitchVisibility;

@Getter
@Setter
public final class PlaylistOperationInput extends CommandInput {

    private int playlistId;

    @Override
    public Command createCommand() {
        return switch (command) {
            case "addRemoveInPlaylist" -> new AddRemoveInPlaylist(this);
            case "switchVisibility" -> new SwitchVisibility(this);
            default -> null;
        };
    }
}
