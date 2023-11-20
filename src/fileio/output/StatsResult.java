package fileio.output;

import java.util.List;
import lombok.Getter;
import main.program.commands.Command;

/**
 * Represents the result of a stats command (ShowPreferredSongs, GetTop5Songs, GetTop5Playlists
 * etc.).
 */
@Getter
public final class StatsResult extends CommandResult {

    private final List<String> result;

    public StatsResult(final Command command, final List<String> result) {
        super(command);
        this.result = result;
    }
}
