package main.commands;

import java.util.List;

public class CommandResult extends Command {
    private final String message;
    private final List<String> results;

    public CommandResult(final String command, final String user, final int timestamp,
                         final String message, final List<String> results) {
        super(command, user, timestamp);
        this.message = message;
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getResults() {
        return results;
    }
}
