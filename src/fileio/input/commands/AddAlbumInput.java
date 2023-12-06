package fileio.input.commands;

import fileio.input.SongInput;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;
import main.program.commands.user.artist.AddAlbum;

@Getter
@Setter
public final class AddAlbumInput extends CommandInputWithName {

    private int releaseYear;
    private String description;
    private List<SongInput> songs;

    @Override
    public Command createCommand() {
        return new AddAlbum(this);
    }
}
