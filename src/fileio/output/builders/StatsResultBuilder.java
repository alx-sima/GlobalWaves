package fileio.output.builders;

import fileio.output.CommandResult;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;

public final class StatsResultBuilder extends ResultBuilder {

    private final StatsResult stats = new StatsResult();

    /**
     * Sets the result of the stats command.
     *
     * @param results the result to be set.
     * @return the updated builder.
     */
    public StatsResultBuilder withResult(final List<String> results) {
        stats.setResult(results);
        return this;
    }

    @Override
    public StatsResultBuilder withCommand(final Command command) {
        stats.setCommand(command.getCommand());
        stats.setUser(command.getUser());
        stats.setTimestamp(command.getTimestamp());
        return this;
    }

    @Override
    public StatsResultBuilder withMessage(final String message) {
        return this;
    }

    @Override
    public CommandResult build() {
        return stats;
    }

    @Getter
    @Setter
    private static final class StatsResult extends CommandResult {

        private List<String> result;
    }
}
