package fileio.output.builders;

import fileio.output.CommandResult;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;

/**
 * A builder for {@link MessageResult}.
 */
public class ResultBuilder {

    private final MessageResult result = new MessageResult();

    /**
     * Sets the common fields of the result (command, user, timestamp).
     *
     * @param command the command whose fields are copied by the result.
     * @return the updated builder.
     */
    public ResultBuilder withCommand(final Command command) {
        result.setCommand(command.getCommand());
        result.setUser(command.getUser());
        result.setTimestamp(command.getTimestamp());
        return this;
    }

    /**
     * Sets the message of the result.
     *
     * @param message the message to be set.
     * @return the updated builder.
     */
    public ResultBuilder withMessage(final String message) {
        result.setMessage(message);
        return this;
    }

    /**
     * Builds the result.
     *
     * @return the result.
     */
    public CommandResult build() {
        return result;
    }

    /**
     * A {@link CommandResult} that contains only a message.
     */
    @Getter
    @Setter
    protected static class MessageResult extends CommandResult {

        private String message;
    }
}
