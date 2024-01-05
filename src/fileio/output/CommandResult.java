package fileio.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

/**
 * A result of a command execution
 */
@Getter
@Setter
public abstract class CommandResult {

    protected String command;
    @JsonInclude(Include.NON_NULL)
    protected String user;
    protected int timestamp;

    protected CommandResult(final String command, final String user, final int timestamp) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
    }
}
