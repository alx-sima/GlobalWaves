package fileio.output;

import lombok.Getter;
import main.program.commands.Command;

@Getter
public final class StatusResult extends CommandResult {

    private final StatusOutput stats;

    public StatusResult(final Command command, final StatusOutput stats) {
        super(command);
        this.stats = stats;
    }
}
