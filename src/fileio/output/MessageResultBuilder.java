package fileio.output;

import fileio.input.commands.CommandInput;
import lombok.Getter;
import lombok.Setter;
import main.program.commands.Command;

/**
 * A builder for {@link MessageResult}.
 */
public final class MessageResultBuilder implements ResultBuilder {

    private final MessageResult result;


    public MessageResultBuilder(final Command command) {
        result = new MessageResult(command);
    }

    public MessageResultBuilder(final CommandInput input) {
        result = new MessageResult(input);
    }

    @Override
    public ResultBuilder withMessage(final String message) {
        result.setMessage(message);
        return this;
    }

    @Override
    public CommandResult build() {
        return result;
    }

    /**
     * A {@link CommandResult} that contains only a message.
     */
    @Getter
    @Setter
    private static final class MessageResult extends CommandResult {

        private String message;

        MessageResult(final Command command) {
            super(command);
        }

        MessageResult(final CommandInput input) {
            super(input);
        }
    }
}
