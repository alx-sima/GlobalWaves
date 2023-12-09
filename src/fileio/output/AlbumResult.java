package fileio.output;

import java.util.List;
import lombok.Getter;
import main.entities.audio.collections.Album;
import main.program.commands.Command;

@Getter
public final class AlbumResult extends CommandResult     {

    private final List<AlbumOutput> result;

    public AlbumResult(final Command command, final List<Album> result) {
        super(command);
        this.result = result.stream().map(AlbumOutput::new).toList();
    }
}

