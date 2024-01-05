package fileio.output;

import lombok.Getter;
import main.program.commands.Command;

@Getter
public abstract class ResultBuilder extends CommandResult {

    protected String message;

    protected ResultBuilder(final Command cmd) {
        super(cmd.getCommand(), cmd.getUser(), cmd.getTimestamp());
    }

    /**
     * Set the result's message and build the result.
     *
     * @return the built result.
     */
    public MessageResult returnMessage(final String returnMessage) {
        message = returnMessage;
        return build();
    }

    /**
     * Build the result.
     */
    public abstract MessageResult build();
}
