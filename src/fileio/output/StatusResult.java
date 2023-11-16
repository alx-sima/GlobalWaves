package fileio.output;

import main.program.commands.Command;

public final class StatusResult extends CommandResult {

    private final StatusOutput stats;

    public StatusResult(final Command command, final StatusOutput stats) {
        super(command);
        this.stats = stats;
    }

    public StatusOutput getStats() {
        return stats;
    }
}
