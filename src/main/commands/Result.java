package main.commands;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public final class Result extends Command {
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<String> results;

    public Result(final Command command, final String message, final List<String> results) {
        super(command);
        this.message = message;
        this.results = results;
    }

    /**
     * Construct a Result that doesn't return any results.
     *
     * @param command The command that generated the result.
     * @param message The result's message.
     */
    public Result(final Command command, final String message) {
        this(command, message, null);
    }

    public String getMessage() {
        return message;
    }

    public List<String> getResults() {
        return results;
    }

    @Override
    public Result execute() {
        return this;
    }
}
