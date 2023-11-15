package fileio.output;

import main.audio.PlayerStatus;
import main.commands.Command;

public final class StatusResult extends CommandResult {

    private final PlayerStatus stats;

    public StatusResult(final Command command, final PlayerStatus stats) {
        super(command);
        this.stats = stats;
    }

    public PlayerStatus getStats() {
        return stats;
    }
}
