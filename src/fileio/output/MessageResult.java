package fileio.output;

import main.commands.Command;

public class MessageResult extends CommandResult {

    private final String message;

    public MessageResult(final Command command, final String message) {
        super(command);
        this.message = message;
    }

    /**
     * Get the message returned by the command.
     */
    public String getMessage() {
        return message;
    }
}
