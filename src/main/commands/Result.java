package main.commands;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public final class Result extends Command {
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<String> results;

    public Result(final String command, final String user, final int timestamp,
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

    @Override
    public Result execute() {
        return this;
    }
}
