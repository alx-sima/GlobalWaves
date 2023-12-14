package fileio.output.builders;

import fileio.output.CommandResult;
import fileio.output.StatusResult;
import fileio.output.StatusResult.StatusOutput;
import main.program.Player;
import main.program.commands.Command;

public final class StatusResultBuilder extends ResultBuilder {

    private final StatusResult result = new StatusResult();

    /**
     * Update the result's stats based on a player at a given moment.
     *
     * @param player    the player that is queried.
     * @param timestamp when the player is queried.
     * @return the updated builder.
     */
    public StatusResultBuilder withPlayer(final Player player, final int timestamp) {
        result.setStats(new StatusOutput(player, timestamp));
        return this;
    }

    @Override
    public StatusResultBuilder withCommand(final Command command) {
        result.setCommand(command.getCommand());
        result.setUser(command.getUser());
        result.setTimestamp(command.getTimestamp());
        return this;
    }

    @Override
    public StatusResultBuilder withMessage(final String message) {
        return this;
    }

    @Override
    public CommandResult build() {
        return result;
    }
}
