package fileio.output;

import lombok.Getter;
import main.program.commands.Command;

@Getter
public class MessageResult extends CommandResult {

    private final String message;

    public MessageResult(final Command command, final String message) {
        super(command);
        this.message = message;
    }
}
